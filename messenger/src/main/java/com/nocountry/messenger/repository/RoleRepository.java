package com.nocountry.messenger.repository;

import com.nocountry.messenger.model.entity.Role;
import com.nocountry.messenger.model.entity.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}