package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.User;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.UserRepository;
import com.example.developpezlebackendenutilisantjavaetspring.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User getUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
