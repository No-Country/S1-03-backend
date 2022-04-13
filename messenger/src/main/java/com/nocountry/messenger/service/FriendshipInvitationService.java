package com.nocountry.messenger.service;

import com.nocountry.messenger.dto.response.FriendInvitationResponse;
import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import org.springframework.stereotype.Service;

@Service
public interface FriendshipInvitationService {
    
    FriendshipInvitation createFriendshipInvitation(String sender, String receiver)
        throws FriendshipInvitationException;

    void respondFriendshipInvitation (FriendInvitationResponse response)
        throws FriendshipInvitationException;

}
