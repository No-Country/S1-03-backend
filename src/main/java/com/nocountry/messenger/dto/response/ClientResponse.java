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
    

    private Set<Role> role; // ver

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    
    private List<Client> friends;
    
    private Set<FriendshipInvitation> sendedInvitations;
            
    private Set<FriendshipInvitation> receivedInvitations;
    

}
