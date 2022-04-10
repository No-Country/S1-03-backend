package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.model.entity.Message;
import com.nocountry.messenger.repository.IClientRepository;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public Message notifyFront(Principal principal, final Message message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        message.setTime(time);
        message.setSender(principal.getName());
        messagingTemplate.convertAndSend("/topic/messages", message);
        
        return message;
    }

    public Message notifyUser(Principal principal, String userNameReceiver, final Message message) {
        
        //Cambiar por verificacion en listaFriend agregados
        boolean isExists = clientRepository.existsByUserName(userNameReceiver);
        if (!isExists) {
            throw new NoSuchElementException(userNameReceiver);
        }
        
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        message.setTime(time);
        message.setSender(principal.getName());
        messagingTemplate.convertAndSendToUser(userNameReceiver, "/queue/messages", message);

        return message;
    }
}
