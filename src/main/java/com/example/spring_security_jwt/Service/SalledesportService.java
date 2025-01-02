package com.example.spring_security_jwt.Service;
import java.time.format.DateTimeFormatter;

import com.example.spring_security_jwt.Entity.Abonnement;
import com.example.spring_security_jwt.Entity.AbonnementDTO;
import com.example.spring_security_jwt.Entity.Salledesport;
import com.example.spring_security_jwt.Entity.SalledesportDTO;
import com.example.spring_security_jwt.Repository.SalledesportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalledesportService {

    @Autowired
    private SalledesportRepository salledesportRepository;

    @Transactional
    public Salledesport createSalledesport(Salledesport salledesport) {
        return salledesportRepository.save(salledesport);
    }

//    public List<Salledesport> getAllSalledesport() {
//        return salledesportRepository.findAll();
//    }

    public SalledesportDTO convertToDTO(Salledesport salledesport) {
        SalledesportDTO dto = new SalledesportDTO();
        dto.setId(salledesport.getId());
        dto.setNomSalle(salledesport.getNomSalle());
        dto.setAdresse(salledesport.getAdresse());
        // Copy other necessary fields
        return dto;
    }

    public List<SalledesportDTO> convertToDTOList(List<Salledesport> salledesports) {
        List<SalledesportDTO> dtoList = new ArrayList<>();
        for (Salledesport salledesport : salledesports) {
            dtoList.add(convertToDTO(salledesport));
        }
        return dtoList;
    }

    public List<SalledesportDTO> getAllSalledesport() {
        List<Salledesport> salles = salledesportRepository.findAll();
        List<SalledesportDTO> sallesDTO = new ArrayList<>();

        // Define a date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern as needed

        for (Salledesport salle : salles) {
            SalledesportDTO salleDTO = new SalledesportDTO();
            salleDTO.setId(salle.getId());
            salleDTO.setNomSalle(salle.getNomSalle());
            salleDTO.setAdresse(salle.getAdresse());
            salleDTO.setNumTel(salle.getNumTel());
            salleDTO.setHeureOuverture(salle.getHeureOuverture());
            salleDTO.setHeureFermeture(salle.getHeureFermeture());

            List<AbonnementDTO> abonnementDTOList = new ArrayList<>();
            for (Abonnement abonnement : salle.getAbonnements()) {
                AbonnementDTO abonnementDTO = new AbonnementDTO();
                abonnementDTO.setId(abonnement.getId());
                abonnementDTO.setNom(abonnement.getNom());
                abonnementDTO.setType(abonnement.getType());
                abonnementDTO.setDescription(abonnement.getDescription());
                abonnementDTO.setPrix(abonnement.getPrix());

                // Convert LocalDate to String
                if (abonnement.getDateDebut() != null) {
                    abonnementDTO.setDateDebut(abonnement.getDateDebut().format(formatter));
                }
                if (abonnement.getDateFin() != null) {
                    abonnementDTO.setDateFin(abonnement.getDateFin().format(formatter));
                }

                abonnementDTOList.add(abonnementDTO);
            }

            salleDTO.setAbonnements(abonnementDTOList);
            sallesDTO.add(salleDTO);
        }

        return sallesDTO;
    }

    public Optional<Salledesport> getSalledesportById(long id) {
        return salledesportRepository.findById(id);
    }

    @Transactional
    public Salledesport updateSalledesport(Salledesport salledesport) {
        return salledesportRepository.save(salledesport);
    }

    @Transactional
    public void deleteSalledesport(long id) {
        if (salledesportRepository.existsById(id)) {
            salledesportRepository.deleteById(id);
        } else {
            throw new RuntimeException("Salledesport not found with id: " + id);
        }
    }
}
