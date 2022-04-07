package com.nocountry.messenger.dto.response;

import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EState;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendInvitationResponse {
    
    private Long idInvitation;

    private Client sender;
    
    private Client receiver;

    private EState state;
    
    private Date creationTimestamp;

    private Date answerTimestamp;
    
}
