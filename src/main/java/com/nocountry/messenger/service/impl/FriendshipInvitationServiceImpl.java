package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.dto.response.ClientResponse;
import com.nocountry.messenger.dto.response.FriendInvitationResponse;
import com.nocountry.messenger.dto.response.FriendshipResponse;
import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import com.nocountry.messenger.repository.FriendshipInvitationRepository;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.security.service.UserDetailsServiceImpl;
import com.nocountry.messenger.service.FriendshipInvitationService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendshipInvitationServiceImpl implements FriendshipInvitationService {

    @Autowired
    private UserDetailsServiceImpl clientServiceImpl;

    @Autowired
    private FriendshipInvitationRepository friendshipInvitationRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public FriendInvitationResponse createFriendshipInvitation(String sender, String receiver)
            throws FriendshipInvitationException {

        Client receiverFriend = clientServiceImpl.getByUserName(receiver);
        Client senderFriend = clientServiceImpl.getByUserName(sender);

        if (senderFriend.getFriends().contains(receiver)) {
            throw new FriendshipInvitationException("Users are friends already");
        } else if (!friendshipInvitationRepository
                .findBySenderAndReceiverAndState(senderFriend, receiverFriend, EFriendshipInvitationState.OPEN).isEmpty()) {
            throw new FriendshipInvitationException("A pending request exists");
        } else if (!friendshipInvitationRepository
                .findBySenderAndReceiverAndState(receiverFriend, senderFriend, EFriendshipInvitationState.OPEN).isEmpty()) {
            throw new FriendshipInvitationException("A pending request exists");
        }

        FriendshipInvitation invitation = new FriendshipInvitation();
        invitation.setSender(senderFriend);
        invitation.setReceiver(receiverFriend);
        invitation.setCreationTimestamp(new Date());
        invitation.setState(EFriendshipInvitationState.OPEN);
        friendshipInvitationRepository.save(invitation);

        //return invitation; // ver
        //Armando respuesta para el front
        ClientResponse receiverResponse = ClientResponse.builder()
                .name(receiverFriend.getName())
                .lastName(receiverFriend.getLastName())
                .userName(receiverFriend.getUserName())
                .build();

        ClientResponse senderResponse = ClientResponse.builder()
                .name(senderFriend.getName())
                .lastName(senderFriend.getLastName())
                .userName(senderFriend.getUserName())
                .build();

        FriendInvitationResponse friendResponse = FriendInvitationResponse.builder()
                .idInvitation(invitation.getIdInvitation())
                .receiver(receiverResponse)
                .sender(senderResponse)
                .state(invitation.getState())
                .build();

        return friendResponse;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void respondFriendshipInvitation(String userName, FriendshipResponse response)
            throws FriendshipInvitationException {

        boolean isExists = friendshipInvitationRepository.existsById(response.getIdInvitation());
        // en caso de declinar no volver a agregar salvo que el que rechace despues quiera agregarlo
        System.out.println(isExists);
        System.out.println(response.getIdInvitation());
        FriendshipInvitation invitation = friendshipInvitationRepository.getById(response.getIdInvitation());
        
        if (isExists && invitation.getState() == EFriendshipInvitationState.OPEN) {

            if (response.getState() == EFriendshipInvitationState.ACCEPTED) {

                Client receiverFriend = clientServiceImpl.getByUserName(userName);
                Client senderFriend = invitation.getSender();
                //Amigos del que recibió la invitación
                List<Client> friendsReceiver = receiverFriend.getFriends();  //Obtengo el set de amigos
                //Agrego el nuevo amigo al set
                friendsReceiver.add(senderFriend);

                //Amigos del que envió la invitación
                List<Client> friendsSender = senderFriend.getFriends();
                //Agrego el nuevo amigo
                friendsSender.add(receiverFriend);

                clientRepository.save(receiverFriend);

                clientRepository.save(senderFriend);

                invitation.setAnswerTimestamp(new Date());
                invitation.setState(response.getState());

                friendshipInvitationRepository.save(invitation);
                System.out.println("IMPRIMIENDO RECEIVER: " + senderFriend.getUserName());
            } else {
                invitation.setAnswerTimestamp(new Date());
                invitation.setState(response.getState());

                friendshipInvitationRepository.save(invitation);
            }
        } else {
            throw new FriendshipInvitationException("Id invitation: " + response.getIdInvitation() + " does not exist.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<FriendshipInvitation> readfriendshipInvitations(Client loggedUserReceiver, EFriendshipInvitationState state) { // receiver = usuario loggeado
        List<FriendshipInvitation> friendshipInvitationList = friendshipInvitationRepository.findByReceiverAndState(loggedUserReceiver, EFriendshipInvitationState.OPEN);
        return friendshipInvitationList;
    }

}
