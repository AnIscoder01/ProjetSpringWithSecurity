package com.example.spring_security_jwt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montantHT;
    private Double tva;
    private Double montantTotal;
    private String description;

    @ManyToOne
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement;
}

