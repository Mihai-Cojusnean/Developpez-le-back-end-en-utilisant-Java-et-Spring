package com.example.developpezlebackendenutilisantjavaetspring.service;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.model.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.repository.RentalRepository;
import com.example.developpezlebackendenutilisantjavaetspring.response.RentalResponse;
import com.example.developpezlebackendenutilisantjavaetspring.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {

    @Value("${image.base-url}")
    private String imageBaseUrl;

    @Value("${image.local-url}")
    private String imageLocalUrl;
    private final RentalRepository rentalRepo;

    public RentalService(RentalRepository rentalRepo) {
        this.rentalRepo = rentalRepo;
    }

    public Rental save(RentalDTO rentalDTO, Authentication authentication) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String imageName = rentalDTO.getPicture().getOriginalFilename();

        saveImage(rentalDTO.getPicture());

        return rentalRepo.save(buildRental(rentalDTO, userDetails, imageName));
    }

    private void saveImage(MultipartFile image) throws IOException {
        String imageName = image.getOriginalFilename();
        Path path = Paths.get(imageLocalUrl + imageName);
        Files.write(path, image.getBytes());
    }

    private Rental buildRental(RentalDTO rentalDTO, UserDetailsImpl userDetails, String imageName) {
        return new Rental(
                rentalDTO.getName(),
                rentalDTO.getSurface(),
                rentalDTO.getPrice(),
                rentalDTO.getDescription(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                userDetails.getId(),
                imageBaseUrl + imageName);
    }

    public RentalResponse getAllByOwnerId(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Rental> rentals = new ArrayList<>(rentalRepo.getAllByOwnerId(userDetails.getId()));

        return new RentalResponse(rentals);
    }

    public Rental getById(Integer id) {
        return rentalRepo.getById(id);
    }
}
