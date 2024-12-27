package com.example.spring_security_jwt.Repository;

import com.example.spring_security_jwt.Entity.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement,Long> {
}
