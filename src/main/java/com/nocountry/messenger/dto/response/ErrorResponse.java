package com.nocountry.messenger.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {

    private String status;
    
    private String code;
    
    private String message;
}
