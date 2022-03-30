package com.nocountry.messenger.repository;

import com.nocountry.messenger.model.entity.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository< Cliente , Long> {
    
    Optional<Cliente> findByUserName(String userName);
    
    List<Cliente> findBySoftDeleteIsNullOrSoftDeleteIsFalse();
    
    boolean existsByUserName(String userName);
    
    boolean existsByMail(String mail);

    public Object findByUsername(String username);

    Boolean existsByUsername(String username);

}
