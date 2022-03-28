package com.nocountry.messenger.dto.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteOkResponse {

    private static String status;
    private static String code;
    private static String message;
    
    public static ResponseEntity<?> deleteOk(HttpStatus statusCode, Long id) {
        status = statusCode.name();
        code = String.valueOf(statusCode.value());
        message = "Id: " + id + " deleted succesfully.";
        Map<String, String> body = new HashMap<>();
        body.put("status", status);
        body.put("code", code);
        body.put("message", message);
        
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
