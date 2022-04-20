package com.nocountry.messenger.service;

import com.nocountry.messenger.dto.response.FriendInvitationResponse;
import com.nocountry.messenger.dto.response.FriendshipResponse;
import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import org.springframework.stereotype.Service;

@Service
public interface FriendshipInvitationService {
    
    FriendInvitationResponse createFriendshipInvitation(String sender, String receiver)
        throws FriendshipInvitationException;

    void respondFriendshipInvitation (String userName, FriendshipResponse response)
        throws FriendshipInvitationException;

}
