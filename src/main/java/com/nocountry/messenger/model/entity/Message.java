package com.nocountry.messenger.model.entity;

import lombok.Data;

@Data
public class Message {
    
    /*
        Modelo utilizado para enviar los mensajes
    */
    private EMessageType type;
    
    private String sender;
    
    private String text;
    
    private String time;
    
}
