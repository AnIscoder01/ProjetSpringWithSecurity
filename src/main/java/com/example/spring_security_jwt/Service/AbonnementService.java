package com.example.spring_security_jwt.Service;


import com.example.spring_security_jwt.Entity.Abonnement;
import com.example.spring_security_jwt.Repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository;


    public Abonnement createAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> getAllAbonnements() {
        return abonnementRepository.findAll();
    }

    public Optional<Abonnement> getAbonnementById(long id) {
        return abonnementRepository.findById(id);
    }

    public Abonnement updateAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    @Transactional
    public void deleteAbonnement(long id) {
        if (abonnementRepository.existsById(id)) {
            abonnementRepository.deleteById(id);
        } else {
            throw new RuntimeException("Abonnement not found with id: " + id);
        }
    }
}
