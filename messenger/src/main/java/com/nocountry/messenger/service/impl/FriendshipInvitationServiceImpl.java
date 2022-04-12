package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import com.nocountry.messenger.repository.FriendshipInvitationRepository;
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
    private FriendshipInvitationRepository friendshipInvitationRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public FriendshipInvitation createFriendshipInvitation(Client sender, Client receiver) 
            
        throws FriendshipInvitationException {
        if (sender.getFriends().contains(receiver)) {
            throw new FriendshipInvitationException("Users are friends already");
        } else if (!friendshipInvitationRepository
                    .findBySenderAndReceiverAndState(sender, receiver, FriendshipInvitation.state.OPEN).isEmpty()) {
            throw new FriendshipInvitationException("A pending request exists");
        } else if (!friendshipInvitationRepository
                    .findBySenderAndReceiverAndState(receiver, sender, FriendshipInvitation.state.OPEN).isEmpty()) {
            throw new FriendshipInvitationException("A pending request exists");
        } 
        
        FriendshipInvitation invitation = new FriendshipInvitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setCreationTimestamp(new Date());
        invitation.setState(FriendshipInvitation.state.OPEN);
        friendshipInvitationRepository.save(invitation);
        
        return invitation; // ver
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void acceptFriendshipInvitation(FriendshipInvitation request, Client receiver)
        throws FriendshipInvitationException {
        
        /*
        if (request.getState() == FriendshipInvitation.state.OPEN) {
            request.setState(FriendshipInvitation.state.ACCEPTED);
          
        }
        */
  
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void declineFriendshipInvitation(FriendshipInvitation request, Client receiver) 
        throws FriendshipInvitationException {
        
        /*
        if (request.getState() == FriendshipInvitation.state.OPEN) {
            request.setState(FriendshipInvitation.state.DECLINED);
        }
        */
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public List<FriendshipInvitation> readfriendshipInvitations(Client loggedUserReceiver, EFriendshipInvitationState state) { // receiver = usuario loggeado
        List<FriendshipInvitation> friendshipInvitationList = friendshipInvitationRepository.findByReceiverAndState(loggedUserReceiver, FriendshipInvitation.state.OPEN);
        return friendshipInvitationList;
    }

}