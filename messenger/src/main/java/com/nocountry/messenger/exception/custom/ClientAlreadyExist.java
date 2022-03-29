package com.nocountry.messenger.exception.custom;

import java.text.MessageFormat;

public class ClientAlreadyExist extends Exception {

    private static final String ERROR_MESSAGE = "Client already exist: {0}";
    
    public ClientAlreadyExist(String userName) {
        super(MessageFormat.format(ERROR_MESSAGE, userName));
    }
}
