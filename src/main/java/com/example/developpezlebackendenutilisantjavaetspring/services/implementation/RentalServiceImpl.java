package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalSaveDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalUpdateDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.models.User;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.RentalRepository;
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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

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

    public void save(RentalSaveDTO rentalSaveDTO,
                     Authentication authentication,
                     HttpServletRequest request) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String imageUrl = saveImage(rentalSaveDTO, request);

        rentalRepo.save(buildRental(rentalSaveDTO, userDetails.getId(), imageUrl));
    }

    private static String saveImage(RentalSaveDTO rentalSaveDTO, HttpServletRequest request) throws IOException {
        MultipartFile file = rentalSaveDTO.getPicture();
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

    public void update(int rentalId, RentalUpdateDTO rentalUpdateDTO, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        Optional<Rental> rental = rentalRepo.getById(rentalId);
        if (rental.isPresent()) {
            if (!Objects.equals(user.getId(), rental.get().getOwnerId())) {
                throw new UnauthorizedException();
            }
            modelMapper.map(rentalUpdateDTO, rental.get());
            rentalRepo.save(rental.get());
        }
    }

    public Rental getById(Integer id) {
        return rentalRepo.getById(id).orElseThrow(() -> new NoSuchElementException("Rental not found"));
    }

    public List<Rental> getAll() {
        return rentalRepo.findAll();
    }

    private Rental buildRental(RentalSaveDTO rentalSaveDTO, int userId, String imageUrl) {
        Rental rental = new Rental();

        modelMapper.map(rentalSaveDTO, rental);
        rental.setOwnerId(userId);
        rental.setPicture(imageUrl);

        return rental;
    }
}
