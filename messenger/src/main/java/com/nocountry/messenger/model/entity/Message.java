package com.nocountry.messenger.model.entity;

import lombok.Data;

@Data
public class Message {
    
    /*
        Modelo utilizado para enviar los mensajes
    */
    private String from;
    
    private String text;
}
