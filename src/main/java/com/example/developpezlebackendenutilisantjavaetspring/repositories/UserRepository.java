package com.example.developpezlebackendenutilisantjavaetspring.repositories;

import com.example.developpezlebackendenutilisantjavaetspring.models.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @NonNull
    @Override
    Optional<User> findById(@NonNull Integer id);

    boolean existsUserByEmail(String email);
}
