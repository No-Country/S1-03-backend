package com.nocountry.messenger.dto.request;

import com.nocountry.messenger.model.entity.Role;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRequest {

    @NotNull(message = "Name cannot be null.")
    private String name;
    
    @NotNull(message = "LastName cannot be null.")
    private String lastName;
    
    @NotNull(message = "UserName cannot be null.")
    private String userName;
    
    @NotNull(message = "Mail cannot be null.")
    private String mail;
    
    @NotNull(message = "Role cannot be null.")
    private Role role;
    
}
