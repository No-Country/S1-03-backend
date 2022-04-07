package com.nocountry.messenger.service.impl;

import com.nocountry.messenger.dto.request.FriendshipInvitationRequest;
import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import com.nocountry.messenger.repository.FriendshipInvitationRepository;
import com.nocountry.messenger.service.FriendshipInvitationService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipInvitationServiceImpl implements FriendshipInvitationService {

    @Autowired
    private FriendshipInvitationRepository friendshipRequestRepository;

    @Override
    public FriendshipInvitationRequest createFriendshipInvitation(Client sender, Client receiver) 
            
        throws FriendshipInvitationException {
        if (sender.getFriends().contains(receiver)) {
            throw new FriendshipInvitationException("Users are friends already");
        } else if (!friendshipRequestRepository
                    .findBySenderAndReceiverAndState(sender, receiver, FriendshipInvitation.state.OPEN).isEmpty()) {
            throw new FriendshipInvitationException("A pending request exists");
        } else if (!friendshipRequestRepository
                    .findBySenderAndReceiverAndState(receiver, sender, FriendshipInvitation.state.OPEN).isEmpty()) {
            throw new FriendshipInvitationException("A pending request exists");
        }
        
        FriendshipInvitation invitation = new FriendshipInvitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setCreationTimestamp(new Date());
        invitation.setState(FriendshipInvitation.state.OPEN);
        friendshipRequestRepository.save(invitation);
        
        return null; //return invitation;
    }

    @Override
    public void acceptFriendshipInvitation(FriendshipInvitationRequest request, Client receiver) 
        throws FriendshipInvitationException {
    }

    @Override
    public void declineFriendshipInvitation(FriendshipInvitationRequest request, Client receiver) 
        throws FriendshipInvitationException {
    }
}