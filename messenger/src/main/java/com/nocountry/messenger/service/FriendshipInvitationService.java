package com.nocountry.messenger.service;

import com.nocountry.messenger.dto.request.FriendshipInvitationRequest;
import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import org.springframework.stereotype.Service;

@Service
public interface FriendshipInvitationService {
    
    FriendshipInvitationRequest createFriendshipInvitation(Client sender, Client receiver)
        throws FriendshipInvitationException;

    void acceptFriendshipInvitation (FriendshipInvitationRequest request, Client receiver)
        throws FriendshipInvitationException;

    void declineFriendshipInvitation(FriendshipInvitationRequest request, Client receiver)
        throws FriendshipInvitationException;
}
