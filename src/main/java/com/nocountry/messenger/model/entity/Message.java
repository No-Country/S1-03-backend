package com.nocountry.messenger.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    
    /*
        Modelo utilizado para enviar los mensajes
    */
    private EMessageType type;
    
    private String sender;
    
    private String text;
    
    private String time;
}
