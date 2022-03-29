package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.dto.request.ClientRequest;
import com.nocountry.messenger.dto.response.ClientResponse;
import com.nocountry.messenger.dto.response.ListClientResponse;
import com.nocountry.messenger.exception.custom.ClientAlreadyExist;
import com.nocountry.messenger.model.entity.Cliente;
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
    public void create(ClientRequest client) throws ClientAlreadyExist {
        
        if(clientRepository.existsByUserName(client.getUserName())) {
            throw new ClientAlreadyExist(client.getUserName());
        } else if( clientRepository.existsByMail(client.getMail())){
            throw new ClientAlreadyExist(client.getMail());
        }
        
        Cliente entity = Cliente.builder()
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
        List<Cliente> clients = clientRepository.findBySoftDeleteIsNullOrSoftDeleteIsFalse();
        System.out.println("PASO POR ACÁ");
        
        ListClientResponse response = new ListClientResponse();
        
        if(clients.isEmpty()) {
            return response;
        }
        List<ClientResponse> clientsResponses = new ArrayList<>();
        for(Cliente client: clients) {
            ClientResponse clientModel = ClientResponse.builder()
                .idClient(client.getIdClient())
                .name(client.getName())
                .lastName(client.getLastName())
                .userName(client.getUserName())
                .mail(client.getMail())
                .role(client.getRole())
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
    public ClientResponse update(ClientRequest client, Long id) throws NoSuchElementException {
        Cliente entity = getById(id);
        
        entity = Cliente.builder()
                .idClient(entity.getIdClient())
                .name(client.getName())
                .lastName(client.getLastName())
                .userName(entity.getUserName())
                .mail(entity.getMail()) //ATRIBUTOS QUE NO DEBEN ACTUALIZARSE POR MAS QUE LO ENVÍEN
                .role(entity.getRole()) //ATRIBUTOS QUE NO DEBEN ACTUALIZARSE POR MAS QUE LO ENVÍEN
                .build();
                //.profileImage(client.getProfileImage())   
                
        clientRepository.save(entity);
        
        return ClientResponse.builder().idClient(entity.getIdClient())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .userName(entity.getUserName())
                .mail(entity.getMail())
                .role(entity.getRole())
                .build();
    }

    @Override
    public void delete(Long id) {   //PROBAR EL CATCH DE NOSUCHELEMENTEXCEPTION
        Cliente client = getById(id);
        
        client.setSoftDelete(true);
        clientRepository.save(client);
    }
    
    //Puede servir para buscar el método buscarAmigo
    private Cliente getByUserName(String userName) {
        return clientRepository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format(USER_NAME_NOT_FOUND, userName)));
    }
    
    private Cliente getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format(USER_ID_NOT_FOUND, id)));
    }
}
