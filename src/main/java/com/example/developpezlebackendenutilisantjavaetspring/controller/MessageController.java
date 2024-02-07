package com.example.developpezlebackendenutilisantjavaetspring.controller;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;
import com.example.developpezlebackendenutilisantjavaetspring.model.Message;
import com.example.developpezlebackendenutilisantjavaetspring.response.MessageResponse;
import com.example.developpezlebackendenutilisantjavaetspring.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> create(@RequestBody MessageDTO messageDTO) {
        messageService.save(messageDTO);

        return ResponseEntity.ok(new MessageResponse(new Message("Message send with success")));
    }
}
