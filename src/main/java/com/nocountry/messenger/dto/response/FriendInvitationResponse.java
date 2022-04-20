package com.nocountry.messenger.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendInvitationResponse {
    
    private Long idInvitation;
    
    private ClientResponse sender;
    
    private ClientResponse receiver;

    private EFriendshipInvitationState state;
    
}
