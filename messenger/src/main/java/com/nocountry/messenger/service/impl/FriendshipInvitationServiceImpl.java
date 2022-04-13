package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.dto.response.FriendInvitationResponse;
import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import com.nocountry.messenger.repository.FriendshipInvitationRepository;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.security.service.UserDetailsServiceImpl;
import com.nocountry.messenger.service.FriendshipInvitationService;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public FriendshipInvitation createFriendshipInvitation(String sender, String receiver)
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

        return invitation; // ver
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void respondFriendshipInvitation(FriendInvitationResponse response)
            throws FriendshipInvitationException {

        boolean isExists = friendshipInvitationRepository.existsById(response.getIdInvitation());

        // en caso de declinar no volver a agregar salvo que el que rechace despues quiera agregarlo
        if (isExists) {
            FriendshipInvitation invitation = friendshipInvitationRepository.getById(response.getIdInvitation()); // y nos ahorramos repetir esto

            if (response.getState() == EFriendshipInvitationState.ACCEPTED) {

                Client receiverFriend = invitation.getReceiver();
                Client senderFriend = invitation.getSender();
                
                Set<Client> senders = new HashSet<>();
                senders.add(senderFriend);
                //ver esto que larga null
                /* 
                Set<Client> receivers = new HashSet<>();
                senders.add(receiverFriend);
                */
                
                receiverFriend.setFriends(senders);
                
                clientRepository.save(receiverFriend);
                
                //friend.builder().state(response.getState()).answerTimestamp(new Date()).build();
            invitation.setAnswerTimestamp(new Date());
            invitation.setState(response.getState());
            //System.out.println("friend " + friend.getState());
            friendshipInvitationRepository.save(invitation);

            }

            /*
                if(response.gerState()==EFriendfshipInvitationState.ACCEPTED){
                agregar a la lista de friends el nuevo amigo. Declarar el otro cliente para setearlo en la otra lista
                obtener el sender con el friend.getSender desde friendshipRepository.getById 
            }
             */
            //friend.builder().state(response.getState()).answerTimestamp(new Date()).build();
            invitation.setAnswerTimestamp(new Date());
            invitation.setState(response.getState());
            //System.out.println("friend " + friend.getState());
            friendshipInvitationRepository.save(invitation);
        }

        /*
        if (request.getState() == FriendshipInvitation.state.OPEN) {
            request.setState(FriendshipInvitation.state.ACCEPTED);
          
        }
         */
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<FriendshipInvitation> readfriendshipInvitations(Client loggedUserReceiver, EFriendshipInvitationState state) { // receiver = usuario loggeado
        List<FriendshipInvitation> friendshipInvitationList = friendshipInvitationRepository.findByReceiverAndState(loggedUserReceiver, EFriendshipInvitationState.OPEN);
        return friendshipInvitationList;
    }

}
