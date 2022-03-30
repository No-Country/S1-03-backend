package com.nocountry.messenger.repository;

import com.nocountry.messenger.model.entity.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nocountry.messenger.model.entity.Role;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository< Role, Long> {
    
    Optional<Role> findByName(ERole name);
}
