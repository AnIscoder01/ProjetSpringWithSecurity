package com.example.spring_security_jwt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Entity
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String type;
    private String description;
    private Double prix;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salledesport salledesport;

    @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL)
    private List<Facture> factures;


    @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL)
    private List<ServiceEntity> services;
}
