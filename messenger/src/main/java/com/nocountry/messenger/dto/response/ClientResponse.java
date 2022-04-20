package com.nocountry.messenger.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import com.nocountry.messenger.model.entity.Role;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponse {

    private Long idClient;
    
    private String name;
    
    private String lastName;
    
    private String userName;
    
    private String mail;
    
    private String profileImage;
    
    private Set<Role> roles; // ver
    
    private List<Client> friends;
    
    private Set<FriendshipInvitation> sendedInvitations;
            
    private Set<FriendshipInvitation> receivedInvitations;
    
}
