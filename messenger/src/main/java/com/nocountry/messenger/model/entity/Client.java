package com.nocountry.messenger.model.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "mail")
        })
public class Client implements Serializable {

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
    
    @Column(name = "phone_number")
    private Long phoneNumber;
    
    @Column(name = "dni")
    private Long document;

    @Column(name = "address")
    @NotBlank
    private String address;

    @Column(name = "birthdate")
    @DateTimeFormat(
            pattern = "yyyy/MM/dd"
    )
    private LocalDate birthdate;
    
    @Column(name = "password")
    private String password;

    @Column(unique = true, name = "mail")
    @Email
    private String mail;

    @Column(name = "profile_image")
    private String profileImage;

    /*
    COMENTADA HASTA REALIZAR LA RELACION BIEN
    @Column(name = "friend_list")
    private List<Client> friendList;
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "soft_delete")
    private boolean softDelete;
}
