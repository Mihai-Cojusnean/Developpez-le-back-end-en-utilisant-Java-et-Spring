package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.Message;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.MessageRepository;
import com.example.developpezlebackendenutilisantjavaetspring.services.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepo;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepo, ModelMapper modelMapper) {
        this.messageRepo = messageRepo;
        this.modelMapper = modelMapper;
    }

    public void save(MessageDTO messageDTO) {
        Message message = new Message();

        modelMapper.map(messageDTO, message);
        messageRepo.save(message);
    }
}
