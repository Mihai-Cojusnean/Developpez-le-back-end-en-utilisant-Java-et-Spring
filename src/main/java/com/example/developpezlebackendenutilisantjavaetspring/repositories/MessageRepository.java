package com.example.developpezlebackendenutilisantjavaetspring.repositories;

import com.example.developpezlebackendenutilisantjavaetspring.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
