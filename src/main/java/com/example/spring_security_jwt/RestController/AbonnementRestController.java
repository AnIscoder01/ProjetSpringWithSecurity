package com.example.spring_security_jwt.RestController;

import com.example.spring_security_jwt.Entity.Abonnement;
import com.example.spring_security_jwt.Service.AbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/abonnement")
public class AbonnementRestController {

    @Autowired
    private AbonnementService abonnementService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/save/{clientId}")
    public Abonnement createAbonnement(@PathVariable long clientId, @RequestBody Abonnement abonnement) {
        // Log incoming request for debugging
        System.out.println("Creating abonnement for client with ID: " + clientId);

        // Check if the client exists and if abonnement is valid
        return abonnementService.createAbonnementForClient(clientId, abonnement);
    }
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping("/new")
   public Abonnement createAbonnement(@RequestBody Abonnement abonnement) {
        return abonnementService.createAbonnement(abonnement);
    }

    @PreAuthorize("hasAnyRole('ADMIN','OWNER','USER')")
    @GetMapping("/by-salle/{salleId}")
    public List<Abonnement> getAbonnementsBySalle(@PathVariable long salleId) {
        return abonnementService.getAbonnementsBySalle(salleId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping("/create-for-salle/{salleId}")
    public Abonnement createAbonnementForSalle(@PathVariable long salleId, @RequestBody Abonnement abonnement) {
        // Log pour vérifier les paramètres
        System.out.println("Creating abonnement for salle with ID: " + salleId);

        // Appeler le service pour la création
        return abonnementService.createAbonnementForSalle(salleId, abonnement);
    }

    @PreAuthorize("hasAnyRole('ADMIN','OWNER','USER')")
    @GetMapping("/all")
    public List<Abonnement> getAllAbonnements() {
        return abonnementService.getAllAbonnements();
    }


    @GetMapping("/getOne/{id}")
    public Abonnement getAbonnementById(@PathVariable long id) {
        return abonnementService.getAbonnementById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public Abonnement updateAbonnement(@PathVariable long id, @RequestBody Abonnement abonnement) {
        Abonnement ExistingAbonnement = abonnementService.getAbonnementById(id).orElse(null);

        if(ExistingAbonnement != null) {
            abonnement.setId(id);
            return abonnementService.updateAbonnement(abonnement);
        }else {
            throw  new RuntimeException("abonnement not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAbonnement(@PathVariable long id) {
        abonnementService.deleteAbonnement(id);
    }
}



