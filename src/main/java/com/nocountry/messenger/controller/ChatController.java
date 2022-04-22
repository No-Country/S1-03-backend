package com.nocountry.messenger.controller;

import com.nocountry.messenger.model.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    
    /*
    @Autowired
    private IClientRepository clientRepository;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    */
    
    //@MessageMapping("/chat.send") VER DIFERENCIA
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message send(Message message) throws Exception {
        return message;
    }
    
    /*
    @MessageMapping("/chat/{to}")   //Implementar este método
    public void newUser(@DestinationVariable String to, Message message) {
        boolean isExists = clientRepository.existsByUserName(to);
        if(isExists) {
            messagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }
    }
    */
}
