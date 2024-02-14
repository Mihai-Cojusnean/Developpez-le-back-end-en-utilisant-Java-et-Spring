package com.example.developpezlebackendenutilisantjavaetspring.security;

import com.example.developpezlebackendenutilisantjavaetspring.models.User;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        return UserDetailsImpl
                .builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .createdAt(user.getCreated_at())
                .updatedAt(user.getUpdated_at())
                .build();
    }
}
