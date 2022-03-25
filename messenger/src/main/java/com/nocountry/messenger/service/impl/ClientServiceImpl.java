package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.dto.request.ClientModel;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.service.IClientService;
import java.text.MessageFormat;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements IClientService {

    private static final String USER_ID_NOT_FOUND = "User id {0} not found.";
    
    @Autowired
    private IClientRepository clientRepository;
    
    @Override
    public void create(ClientModel client) {
        Client entity = Client.builder()
                .lastName(client.getLastName())
                .name(client.getName())
                .userName(client.getUserName())
                .mail(client.getMail())
                .role(client.getRole())
                .build();
        
        clientRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Client client = getById(id);
        
        clientRepository.delete(client);
    }

    @Override
    public Optional<Client> update(ClientModel client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format(USER_ID_NOT_FOUND, id)));
    }
}
