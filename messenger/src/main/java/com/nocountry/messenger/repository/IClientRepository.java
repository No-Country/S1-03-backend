package com.nocountry.messenger.repository;

import com.nocountry.messenger.model.entity.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository< Client, Long> {

    Optional<Client> findByUserName(String userName);
    
    Client getByUserName(String userName);
    
    Boolean existsByUserName(String userName);

    Boolean existsByMail(String email);
    
    List<Client> findBySoftDeleteIsNullOrSoftDeleteIsFalse();
    
    
}
