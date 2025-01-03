package com.example.spring_security_jwt.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SalledesportDTO {
    private Long id;
    private String nomSalle;
    private String adresse;
    private String numTel;
    private String heureOuverture;
    private String heureFermeture;
    private String ownerUsername; // Ajouter le propriétaire si nécessaire

    private List<AbonnementDTO> abonnements;

}
