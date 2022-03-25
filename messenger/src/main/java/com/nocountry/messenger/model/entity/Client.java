package com.nocountry.messenger.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Client")
public class Client implements Serializable {

    private static final Long SerialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "clientname")
    private String clientname;
    @Column(name = "mail")
    private String mail;
    @Column(name = "profilImage")
    private String profileImage;
    @Column(name = "friendList")
    private List<Client> friendlist;
    private Rol rol; // ver
    
    
}
