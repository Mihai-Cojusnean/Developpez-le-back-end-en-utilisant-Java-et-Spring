package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.InvalidMessageException;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.models.Message;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.MessageRepository;
import com.example.developpezlebackendenutilisantjavaetspring.services.MessageService;
import com.example.developpezlebackendenutilisantjavaetspring.services.RentalService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepo;
    private final RentalService rentalService;

    public MessageServiceImpl(MessageRepository messageRepo, RentalService rentalService) {
        this.messageRepo = messageRepo;
        this.rentalService = rentalService;
    }

    public void save(MessageDTO messageDTO) {
        validateMessageDTO(messageDTO);

        int userId = rentalService
                .getById(messageDTO.getRentalId())
                .getOwnerId();

        ensureUserIsNotOwner(userId, messageDTO.getUserId());

        messageRepo.save(new Message(
                messageDTO.getUserId(),
                messageDTO.getRentalId(),
                messageDTO.getMessage()));
    }

    private void validateMessageDTO(MessageDTO messageDTO) {
        if (messageDTO.getMessage().isEmpty()) {
            throw new InvalidMessageException("Message cannot be empty");
        }
    }

    private void ensureUserIsNotOwner(int userId, int ownerId) {
        if (userId == ownerId) {
            throw new UnauthorizedException();
        }
    }
}
