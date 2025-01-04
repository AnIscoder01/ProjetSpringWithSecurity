package com.example.spring_security_jwt.Service;


import com.example.spring_security_jwt.Entity.Abonnement;
import com.example.spring_security_jwt.Entity.Salledesport;
import com.example.spring_security_jwt.Entity.User;
import com.example.spring_security_jwt.Repository.AbonnementRepository;
import com.example.spring_security_jwt.Repository.SalledesportRepository;
import com.example.spring_security_jwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository;
    @Autowired
    private SalledesportRepository salledesportRepository;
    @Autowired
    private UserRepository userRepository;

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

    // Create abonnement and associate it with the client
    public Abonnement createAbonnementForClient(long clientId, Abonnement abonnement) {
        // Check if client exists
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        // Set the client to the abonnement
        abonnement.setClient(client);

        // Ensure that the abonnement's salledesport is set if needed
        if (abonnement.getSalledesport() != null && abonnement.getSalledesport().getId() != null) {
            // You can fetch and set the Salledesport if needed
        }

        // Save and return the abonnement
        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> getAbonnementsBySalle(long salleId) {
        // Vérifier que la salle existe
        Salledesport salle = salledesportRepository.findById(salleId)
                .orElseThrow(() -> new RuntimeException("Salle de sport not found"));

        // Retourner les abonnements de cette salle
        return abonnementRepository.findBySalledesport(salle);
    }

    public Abonnement createAbonnementForSalle(long salleId, Abonnement abonnement) {
        // Récupérer la salle depuis le repository
        Salledesport salle = salledesportRepository.findById(salleId)
                .orElseThrow(() -> new RuntimeException("Salle de sport not found"));

        // Associer la salle à l'abonnement
        abonnement.setSalledesport(salle);

        // Enregistrer l'abonnement
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
