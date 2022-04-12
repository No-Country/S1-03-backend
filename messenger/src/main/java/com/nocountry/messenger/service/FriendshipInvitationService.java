package com.nocountry.messenger.service;

import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import org.springframework.stereotype.Service;

@Service
public interface FriendshipInvitationService {
    
    FriendshipInvitation createFriendshipInvitation(Client sender, Client receiver)
        throws FriendshipInvitationException;

    void acceptFriendshipInvitation (FriendshipInvitation request, Client receiver)
        throws FriendshipInvitationException;

    void declineFriendshipInvitation(FriendshipInvitation request, Client receiver)
        throws FriendshipInvitationException;
}
