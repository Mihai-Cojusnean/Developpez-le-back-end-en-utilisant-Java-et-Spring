package com.example.developpezlebackendenutilisantjavaetspring.controllers;

import com.example.developpezlebackendenutilisantjavaetspring.dto.MessageDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.InvalidMessageException;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.responses.MessageResponse;
import com.example.developpezlebackendenutilisantjavaetspring.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Post message", description = "Post message under a rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message send with success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody MessageDTO messageDTO) {
        try {
            messageService.save(messageDTO);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (InvalidMessageException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new MessageResponse("Message sent with success"));
    }
}
