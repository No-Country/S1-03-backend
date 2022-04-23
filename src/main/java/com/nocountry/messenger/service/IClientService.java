package com.nocountry.messenger.service;

import com.nocountry.messenger.dto.request.ClientRequest;
import com.nocountry.messenger.dto.response.ClientResponse;
import com.nocountry.messenger.dto.response.ListClientResponse;

import com.nocountry.messenger.exception.custom.ClientAlreadyExist;


import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public interface IClientService {
    
    //void create(ClientRequest client) throws ClientAlreadyExist;
    
    void delete(Long id);
    
    ClientResponse update(ClientRequest client, Long id) throws NoSuchElementException;
    
    ListClientResponse listClients();
}
