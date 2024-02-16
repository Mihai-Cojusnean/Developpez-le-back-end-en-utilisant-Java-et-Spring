package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.SaveRentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.PutRentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.ResourceNotFoundException;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.RentalRepository;
import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.responses.RentalsResponse;
import com.example.developpezlebackendenutilisantjavaetspring.dto.UserDTO;
import com.example.developpezlebackendenutilisantjavaetspring.security.UserDetailsImpl;
import com.example.developpezlebackendenutilisantjavaetspring.services.RentalService;
import com.example.developpezlebackendenutilisantjavaetspring.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepo;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public RentalServiceImpl(RentalRepository rentalRepo, ModelMapper modelMapper, UserService userService) {
        this.rentalRepo = rentalRepo;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public void save(SaveRentalDTO saveRentalDTO,
                     Authentication authentication,
                     HttpServletRequest request) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String imageUrl = saveImage(saveRentalDTO, request);

        rentalRepo.save(buildRental(saveRentalDTO, userDetails.getId(), imageUrl));
    }

    private static String saveImage(SaveRentalDTO saveRentalDTO, HttpServletRequest request) throws IOException {
        MultipartFile file = saveRentalDTO.getPicture();
        String fileName = file.getOriginalFilename();
        String filePath = request.getSession().getServletContext().getRealPath("/uploads/");

        File dest = new File(filePath + fileName);
        if (!dest.exists()) {
            new File(filePath).mkdirs();
        }
        file.transferTo(dest);

        String relativePath = "/uploads/" + fileName;

        return String.format("%s://%s:%s%s%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                request.getContextPath(),
                relativePath);
    }

    public void update(int rentalId, PutRentalDTO putRentalDTO, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        Rental rental = rentalRepo.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + rentalId));

        if (!Objects.equals(user.getId(), rental.getOwnerId())) {
            throw new UnauthorizedException();
        }
        modelMapper.map(putRentalDTO, rental);
        rentalRepo.save(rental);
    }

    @Override
    public RentalsResponse getAll() {
        List<Rental> rentalsResponse = rentalRepo.findAll();
        return new RentalsResponse(rentalsResponse);
    }

    public RentalDTO getById(Integer id) {
        return rentalRepo.findById(id)
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + id));
    }


    private Rental buildRental(SaveRentalDTO saveRentalDTO, int userId, String imageUrl) {
        Rental rental = new Rental();

        modelMapper.map(saveRentalDTO, rental);
        rental.setOwnerId(userId);
        rental.setPicture(imageUrl);

        return rental;
    }
}
