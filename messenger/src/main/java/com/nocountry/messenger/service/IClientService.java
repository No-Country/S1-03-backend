package com.nocountry.messenger.service;

import com.nocountry.messenger.dto.request.ClientModel;
import com.nocountry.messenger.dto.response.ListClientResponse;
import org.springframework.stereotype.Service;

@Service
public interface IClientService {
    
    void create(ClientModel client);
    
    void deleteById(Long id);
    
    ListClientResponse listClients();
    
    ClientModel update(ClientModel client);
}
