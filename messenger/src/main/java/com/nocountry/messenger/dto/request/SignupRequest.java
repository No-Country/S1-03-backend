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
    @Size(max = 50)
    @Email
    private String mail;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private Set<String> role;

    public SignupRequest() {
    }