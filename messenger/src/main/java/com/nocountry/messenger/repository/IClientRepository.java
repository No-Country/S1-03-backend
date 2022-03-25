package com.nocountry.messenger.repository;

import com.nocountry.messenger.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository< Client , Long> {
    
}
