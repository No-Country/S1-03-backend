package com.nocountry.messenger.dto.response;

import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import lombok.Data;

@Data
public class FriendshipResponse {

    private Long idInvitation;

    private EFriendshipInvitationState state;
}
