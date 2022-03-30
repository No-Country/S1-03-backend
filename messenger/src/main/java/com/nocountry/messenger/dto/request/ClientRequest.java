package com.nocountry.messenger.dto.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRequest {

    @NotBlank
    @Size(min = 3, max = 25)
    private String name;

    @NotBlank
    @Size(min = 3, max = 25)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 40)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Long phoneNumber;

    private Long document;

    @NotBlank
    @Size(min = 3, max = 40)
    private String address;

    private Long addressNumber;

    @NotBlank
    private String birthdate;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private Set<String> role;
    
}
