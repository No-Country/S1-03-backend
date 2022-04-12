package com.nocountry.messenger.dto.response;

import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendInvitationResponse {
    
    private Long idInvitation;

    private Client sender;
    
    private Client receiver;

    private EFriendshipInvitationState state;
    
    private Date creationTimestamp;

    private Date answerTimestamp;
    
}
