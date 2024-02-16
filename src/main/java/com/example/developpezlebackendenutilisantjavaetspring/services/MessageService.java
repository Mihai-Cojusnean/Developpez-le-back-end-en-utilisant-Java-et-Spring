package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.InvalidMessageException;

public interface MessageService {
    /**
     * Saves the message.
     *
     * @param messageDTO The message data transfer object.
     * @throws InvalidMessageException If message is empty.
     */
    void save(MessageDTO messageDTO);
}
