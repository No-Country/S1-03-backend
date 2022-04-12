package com.nocountry.messenger.dto.response;

import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendInvitationResponse {
    
    private Long idInvitation;

    private EFriendshipInvitationState state;
    
}
