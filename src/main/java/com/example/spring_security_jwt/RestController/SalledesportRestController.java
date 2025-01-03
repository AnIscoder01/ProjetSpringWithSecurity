package com.example.spring_security_jwt.RestController;
import com.example.spring_security_jwt.Entity.Salledesport;
import com.example.spring_security_jwt.Entity.SalledesportDTO;
import com.example.spring_security_jwt.Service.SalledesportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/salledesport")
public class SalledesportRestController {
    @Autowired
    private SalledesportService salledesportService;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/my-salles")
    public List<SalledesportDTO> getMySalles() {
        return salledesportService.getSalledesportsByOwner();
    }


    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/save")
    public Salledesport createSalledesport(@RequestBody Salledesport salledesport) {
        return salledesportService.createSalledesport(salledesport);
    }
    @GetMapping("/all")
    public List<SalledesportDTO> getAllSallesdesport() {
        return salledesportService.getAllSalledesport();
    }
    @GetMapping("/getOne/{id}")
    public Salledesport getSalledesportById(@PathVariable long id) {
        return salledesportService.getSalledesportById(id).orElse(null);
    }
    @PutMapping("/update/{id}")
    public Salledesport updateSalledesport(@PathVariable long id, @RequestBody Salledesport salledesport) {
        Salledesport existingSalledesport = salledesportService.getSalledesportById(id).orElse(null);

        if (existingSalledesport != null) {
            salledesport.setId(id);
            return salledesportService.updateSalledesport(salledesport);
        } else {
            throw new RuntimeException("Salledesport not found");
        }
    }
    @DeleteMapping("/delete/{id}")
    public void deleteSalledesport(@PathVariable long id) {
        salledesportService.deleteSalledesport(id);
    }
}