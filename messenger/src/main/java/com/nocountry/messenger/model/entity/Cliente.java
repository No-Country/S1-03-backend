package com.nocountry.messenger.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "lastname")
    private String lastName;
    
    @Column(unique = true, name = "username")
    private String userName;
    
    @Column(unique = true, name = "mail")
    private String mail;
    
    @Column(unique = true, name = "password")
    private String password;
    
    @Column(name = "profile_image")
    private String profileImage;
    
    /*
    COMENTADA HASTA REALIZAR LA RELACION BIEN
    @Column(name = "friend_list")
    private List<Client> friendList;
    */
    
    @Column(name = "role")
    private Role role; // ver
    
    @Column(name = "soft_delete")
    private boolean softDelete;
}
