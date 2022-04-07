package com.nocountry.messenger.security.service;

import com.nocountry.messenger.dto.request.ClientRequest;
import com.nocountry.messenger.dto.response.ClientResponse;
import com.nocountry.messenger.dto.response.ListClientResponse;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.security.UserDetailsImpl;
import com.nocountry.messenger.service.IClientService;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, IClientService {

    private static final String USER_ID_NOT_FOUND = "User id {0} not found.";
    private static final String USER_NAME_NOT_FOUND = "UserName {0} not found.";

    @Autowired
    IClientRepository clientRepository;

    // Interfaz User Detail
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client user = clientRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }
    
    // Interfaz Client
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public ListClientResponse listClients() {
        List<Client> clients = clientRepository.findBySoftDeleteIsNullOrSoftDeleteIsFalse();
        System.out.println("PASO POR ACÁ");

        ListClientResponse response = new ListClientResponse();

        if (clients.isEmpty()) {
            return response;
        }
        List<ClientResponse> clientsResponses = new ArrayList<>();
        for (Client client : clients) {
            ClientResponse clientModel = ClientResponse.builder()
                    .idClient(client.getIdClient())
                    .name(client.getName())
                    .lastName(client.getLastName())
                    .userName(client.getUserName())
                    .mail(client.getMail())
                    .roles(client.getRoles())
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public ClientResponse update(ClientRequest client, Long id) throws NoSuchElementException {
        Client entity = getById(id);

        entity = Client.builder()
                .idClient(entity.getIdClient())
                .name(client.getName())
                .lastName(client.getLastName())
                .userName(entity.getUserName())
                .mail(entity.getMail()) //ATRIBUTOS QUE NO DEBEN ACTUALIZARSE POR MAS QUE LO ENVÍEN
                .roles(entity.getRoles()) //ATRIBUTOS QUE NO DEBEN ACTUALIZARSE POR MAS QUE LO ENVÍEN
                .build();
        //.profileImage(client.getProfileImage())   

        clientRepository.save(entity);

        return ClientResponse.builder().idClient(entity.getIdClient())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .userName(entity.getUserName())
                .mail(entity.getMail())
                .roles(entity.getRoles())
                .build();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void delete(Long id) {   //PROBAR EL CATCH DE NOSUCHELEMENTEXCEPTION
        Client client = getById(id);

        client.setSoftDelete(true);
        clientRepository.save(client);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format(USER_ID_NOT_FOUND, id)));
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public Client getByUserName(String userName) {
        return clientRepository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format(USER_NAME_NOT_FOUND, userName)));
    }

    
}
