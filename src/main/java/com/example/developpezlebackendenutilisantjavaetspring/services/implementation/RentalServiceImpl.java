package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.RentalRepository;
import com.example.developpezlebackendenutilisantjavaetspring.responses.RentalResponse;
import com.example.developpezlebackendenutilisantjavaetspring.security.UserDetailsImpl;
import com.example.developpezlebackendenutilisantjavaetspring.services.RentalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RentalServiceImpl implements RentalService {

    @Value("${image.base-url}")
    private String imageBaseUrl;

    @Value("${image.local-url}")
    private String imageLocalUrl;

    private final RentalRepository rentalRepo;
    private final ModelMapper modelMapper;

    public RentalServiceImpl(RentalRepository rentalRepo, ModelMapper modelMapper) {
        this.rentalRepo = rentalRepo;
        this.modelMapper = modelMapper;
    }

    public Rental save(RentalDTO rentalDTO, Authentication authentication) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String imageName = rentalDTO.getPicture().getOriginalFilename();

        saveImage(rentalDTO.getPicture(), imageName);

        return rentalRepo.save(buildRental(rentalDTO, userDetails.getId(), imageName));
    }

    public Rental getById(Integer id) {
        return rentalRepo.getById(id).orElseThrow(() -> new NoSuchElementException("Rental not found"));
    }

    public RentalResponse getAllByOwnerId(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Rental> rentals = new ArrayList<>(rentalRepo.getAllByOwnerId(userDetails.getId()));

        return new RentalResponse(rentals);
    }

    private void saveImage(MultipartFile image, String imageName) throws IOException {
        Path path = Paths.get(imageLocalUrl + imageName);
        Files.write(path, image.getBytes());
    }

    private Rental buildRental(RentalDTO rentalDTO, int userId, String imageName) {
        Rental rental = new Rental();

        modelMapper.map(rentalDTO, rental);
        rental.setOwnerId(userId);
        rental.setPicture(imageBaseUrl + imageName);

        return rental;
    }
}
