package com.example.spring_security_jwt.Repository;

import com.example.spring_security_jwt.Entity.Salledesport;
import com.example.spring_security_jwt.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalledesportRepository extends JpaRepository<Salledesport,Long> {
    List<Salledesport> findByOwner(User owner);

}
