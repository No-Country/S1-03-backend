package com.nocountry.messenger.controller;

import com.nocountry.messenger.exception.custom.ExceptionHandler;
import com.nocountry.messenger.model.entity.Message;
import com.nocountry.messenger.service.impl.WebSocketServiceImpl;
import java.security.Principal;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    
    @Autowired
    private WebSocketServiceImpl wsService;
    
    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody Message message, Principal principal) {
        /*
            Utilizado para enviar mensajes a todos los usuarios
        */
        return new ResponseEntity<>( wsService.notifyFront(principal, message), HttpStatus.OK);
    }
    
    @PostMapping("/send-private-message/{userNameReceiver}")
    public ResponseEntity<?> sendPrivateMessage(
            @PathVariable String userNameReceiver , @RequestBody Message message, Principal principal
        ){
        /*
            Utilizado para enviar mensaje a un usuario en específico
        */
            try {
                return new ResponseEntity<>( wsService.notifyUser(principal, userNameReceiver, message), HttpStatus.OK);
            } catch (NoSuchElementException e) {
                String errorMessage = "Username " + e.getMessage()+ " not found.";
                return ExceptionHandler.throwError(HttpStatus.NOT_FOUND, errorMessage);
            }
    }
}
