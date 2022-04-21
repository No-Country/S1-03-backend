package com.nocountry.messenger.dto.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

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
    @Size(min = 3, max = 13)
    private Long phoneNumber;
        
    @NotBlank
    @Size(min = 3, max = 10)
    private Long document;
    
    @NotBlank
    @Size(min = 3, max = 40)
    private String adress;

    @NotBlank
    @Size(max = 50)
    @Email
    private String mail;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private Set<String> role;

    public SignupRequest() {
    }
} 