package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.Message;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.MessageRepository;
import com.example.developpezlebackendenutilisantjavaetspring.services.MessageService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepo;

    public MessageServiceImpl(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    public void save(MessageDTO messageDTO, Principal principal) {
        messageRepo.save(new Message(
                messageDTO.getUserId(),
                messageDTO.getRentalId(),
                messageDTO.getMessage()));
    }
}
