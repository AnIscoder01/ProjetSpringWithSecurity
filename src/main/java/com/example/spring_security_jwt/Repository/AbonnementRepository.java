package com.example.spring_security_jwt.Repository;

import com.example.spring_security_jwt.Entity.Abonnement;
import com.example.spring_security_jwt.Entity.Salledesport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement,Long> {

    List<Abonnement> findBySalledesport(Salledesport salledesport);

}
