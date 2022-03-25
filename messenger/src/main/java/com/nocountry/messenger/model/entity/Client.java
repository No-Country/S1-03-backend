package com.nocountry.messenger.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "Client")
@Builder
public class Client implements Serializable {

    private static final Long SerialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username")
    private String userName;
    @Column(name = "mail")
    private String mail;
    @Column(name = "profile_image")
    private String profileImage;
    /*
    COMENTADA HASTA REALIZAR LA RELACION BIEN
    @Column(name = "friend_list")
    private List<Client> friendList;
    */
    private Role role; // ver
    
    
}
