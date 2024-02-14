package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;

public interface MessageService {
    /**
     * Saves the message.
     *
     * @param messageDTO The message data transfer object.
     */
    void save(MessageDTO messageDTO);
}
