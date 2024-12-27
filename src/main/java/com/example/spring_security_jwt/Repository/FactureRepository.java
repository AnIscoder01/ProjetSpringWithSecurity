package com.example.spring_security_jwt.Repository;

import com.example.spring_security_jwt.Entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture,Long> {
}
