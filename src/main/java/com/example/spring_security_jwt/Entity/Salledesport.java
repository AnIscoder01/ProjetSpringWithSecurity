package com.example.spring_security_jwt.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Salledesport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomSalle;
    private String adresse;
    private String numTel;
    private String heureOuverture;
    private String heureFermeture;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // Getters et setters
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToMany(mappedBy = "salledesport", cascade = CascadeType.ALL)
    private List<Abonnement> abonnements;
}

