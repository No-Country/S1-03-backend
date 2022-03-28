package com.nocountry.messenger.exception.custom;

import com.nocountry.messenger.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {

    public static ResponseEntity<?> throwError(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        
        errorResponse.setStatus(status.name());
        errorResponse.setCode(String.valueOf(status.value()));
        errorResponse.setMessage(message);
        
        return new ResponseEntity<>(errorResponse, status);
    }
}
