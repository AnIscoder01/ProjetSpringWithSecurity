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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;  // Many gyms can belong to one owner

    @OneToMany(mappedBy = "salledesport", cascade = CascadeType.ALL)
    private List<Abonnement> abonnements;
}

