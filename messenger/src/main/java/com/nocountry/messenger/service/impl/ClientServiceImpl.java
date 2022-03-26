package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.dto.request.ClientModel;
import com.nocountry.messenger.dto.response.ClientResponse;
import com.nocountry.messenger.dto.response.ListClientResponse;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.service.IClientService;
import java.text.MessageFormat;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class ClientServiceImpl implements IClientService {

    private static final String USER_ID_NOT_FOUND = "User id {0} not found.";
    private static final String USER_NAME_NOT_FOUND = "UserName {0} not found.";
    
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
    public ListClientResponse listClients() {
        List<Client> clients = clientRepository.findBySoftDeleteIsNullOrSoftDeleteIsFalse();
        
        ListClientResponse response = new ListClientResponse();
        
        if(clients.isEmpty()) {
            return response;
        }
        
        List<ClientResponse> clientsResponses = new ArrayList<>();
        for(Client client: clients) {
            ClientResponse clientModel = ClientResponse.builder()
                .idClient(client.getIdClient())
                .lastName(client.getLastName())
                .mail(client.getMail())
                .name(client.getName())
                .build();
            
            /*
                AGREGAR LÓGICA PARA AGREGAR LA LISTA DE AMIGOS
            */
            
            clientsResponses.add(clientModel);
        }
        
        response.setClients(clientsResponses);
        return response;
    }
    
    @Override
    public ClientModel update(ClientModel client) {
        Client entity = getByUserName(client.getUserName());
        
        entity = Client.builder().idClient(entity.getIdClient())
                .lastName(client.getLastName())
                .mail(client.getMail())
                .name(client.getName())
                .build();
                //.profileImage(client.getProfileImage())   
                
        clientRepository.save(entity);
        return client;
    }

    @Override
    public void deleteById(Long id) {
        Client client = getById(id);
        
        client.setSoftDelete(true);
        clientRepository.delete(client);
    }
    
    private Client getByUserName(String userName) {
        return clientRepository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format(USER_NAME_NOT_FOUND, userName)));
    }
    
    private Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format(USER_ID_NOT_FOUND, id)));
    }
}
