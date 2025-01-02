package com.example.spring_security_jwt.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbonnementDTO {
    private Long id;
    private String nom;
    private String type;
    private String description;
    private Double prix;
    private String dateDebut;  // Keep as String
    private String dateFin;    // Keep as String

    // Getters and setters
    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}

