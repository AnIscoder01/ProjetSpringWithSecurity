package com.example.spring_security_jwt.Repository;

import com.example.spring_security_jwt.Entity.ERole;
import com.example.spring_security_jwt.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
