package com.example.spring_security_jwt.Service;
import java.time.format.DateTimeFormatter;

import com.example.spring_security_jwt.Entity.*;
import com.example.spring_security_jwt.Repository.SalledesportRepository;
import com.example.spring_security_jwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalledesportService {

    @Autowired
    private SalledesportRepository salledesportRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Salledesport createSalledesport(Salledesport salledesport) {
        // Obtenir l'utilisateur connecté via Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Trouver l'utilisateur connecté dans la base de données
        Optional<User> ownerOpt = userRepository.findByUsername(username);
        if (ownerOpt.isPresent()) {
            User owner = ownerOpt.get();

            // Associer le propriétaire à la salle de sport
            salledesport.setOwner(owner);

            // Sauvegarder la salle de sport
            return salledesportRepository.save(salledesport);
        } else {
            throw new RuntimeException("Utilisateur connecté non trouvé");
        }
    }



//    public List<Salledesport> getAllSalledesport() {
//        return salledesportRepository.findAll();
//    }

    public SalledesportDTO convertToDTO(Salledesport salledesport) {
        SalledesportDTO dto = new SalledesportDTO();
        dto.setId(salledesport.getId());
        dto.setNomSalle(salledesport.getNomSalle());
        dto.setAdresse(salledesport.getAdresse());
        dto.setNumTel(salledesport.getNumTel());
        dto.setHeureOuverture(salledesport.getHeureOuverture());
        dto.setHeureFermeture(salledesport.getHeureFermeture());

        if (salledesport.getOwner() != null) {
            dto.setOwnerUsername(salledesport.getOwner().getUsername());
        }

        return dto;
    }


    public List<SalledesportDTO> getSalledesportsByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> ownerOpt = userRepository.findByUsername(username);
        if (ownerOpt.isPresent()) {
            List<Salledesport> salles = salledesportRepository.findByOwner(ownerOpt.get());
            return convertToDTOList(salles);
        } else {
            throw new RuntimeException("Utilisateur connecté non trouvé");
        }
    }

    public List<SalledesportDTO> convertToDTOList(List<Salledesport> salledesports) {
        List<SalledesportDTO> dtoList = new ArrayList<>();
        for (Salledesport salledesport : salledesports) {
            dtoList.add(convertToDTO(salledesport));
        }
        return dtoList;
    }
    public Optional<User> getOwnerByUsername(String username) {
        return userRepository.findByUsername(username); // Assuming you have a method to find user by username
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> ownerOpt = userRepository.findByUsername(username);
        if (ownerOpt.isPresent() && salledesport.getOwner().equals(ownerOpt.get())) {
            return salledesportRepository.save(salledesport);
        } else {
            throw new RuntimeException("Permission refusée : vous ne pouvez modifier que vos propres salles.");
        }
    }

    @Transactional
    public void deleteSalledesport(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> ownerOpt = userRepository.findByUsername(username);
        Optional<Salledesport> salledesportOpt = salledesportRepository.findById(id);

        if (ownerOpt.isPresent() && salledesportOpt.isPresent() &&
                salledesportOpt.get().getOwner().equals(ownerOpt.get())) {
            salledesportRepository.deleteById(id);
        } else {
            throw new RuntimeException("Permission refusée : vous ne pouvez supprimer que vos propres salles.");
        }
    }
}
