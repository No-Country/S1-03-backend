package com.nocountry.messenger.service;

import com.nocountry.messenger.dto.request.ClientModel;
import com.nocountry.messenger.model.entity.Client;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface IClientService {
    
    void create(ClientModel client);
    
    void deleteById(Long id);
    
    Optional<Client> update(ClientModel client);
}
