package com.example.developpezlebackendenutilisantjavaetspring.service;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;
import com.example.developpezlebackendenutilisantjavaetspring.model.Message;
import com.example.developpezlebackendenutilisantjavaetspring.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {
    private final MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    public void save(MessageDTO messageDTO) {
        messageRepo.save(new Message(
                messageDTO.getUser_id(),
                messageDTO.getRental_id(),
                messageDTO.getMessage(),
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
    }
}
