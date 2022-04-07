package com.nocountry.messenger.dto.request;

import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EState;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendshipInvitationRequest {
    
    @NotBlank
    @Size(min = 3, max = 40)
    private Client sender;
    
    @NotBlank
    @Size(min = 3, max = 40)
    private Client receiver;
    
    private EState state;
}
