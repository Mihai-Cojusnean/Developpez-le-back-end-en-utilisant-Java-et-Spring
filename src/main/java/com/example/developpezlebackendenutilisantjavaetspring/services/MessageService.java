package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;

import java.security.Principal;

public interface MessageService {
    /**
     * Saves the message.
     *
     * @param messageDTO  The message data transfer object.
     * @param principal   The authenticated user.
     */
    void save(MessageDTO messageDTO, Principal principal);
}
