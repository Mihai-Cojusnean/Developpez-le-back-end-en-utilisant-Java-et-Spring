package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.auth.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.UserDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UserNotFoundException;
import com.example.developpezlebackendenutilisantjavaetspring.models.User;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.UserRepository;
import com.example.developpezlebackendenutilisantjavaetspring.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepo, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public UserDTO getUserById(int id) {
        return userRepo.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserDTO getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public void register(RegisterDTO registerDTO) {
        User user = new User();

        modelMapper.map(registerDTO, user);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepo.save(user);
    }

    public boolean userExists(String email) {
        return userRepo.existsUserByEmail(email);
    }
}
