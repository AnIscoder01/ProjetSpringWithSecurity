package com.example.spring_security_jwt.RestController;


import com.example.spring_security_jwt.Entity.Facture;
import com.example.spring_security_jwt.Service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facture")
public class FactureRestController {

    @Autowired
    private FactureService factureService;

    @PostMapping("/save")
    public Facture createFacture(@RequestBody Facture facture) {
        return factureService.createFacture(facture);
    }

    @GetMapping("/all")
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }

    @GetMapping("/getOne/{id}")
    public Facture getFactureById(@PathVariable long id) {
        return factureService.getFactureById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public Facture updateFacture(@PathVariable long id, @RequestBody Facture facture) {
        Facture existingFacture = factureService.getFactureById(id).orElse(null);

        if (existingFacture != null) {
            facture.setId(id);
            return factureService.updateFacture(facture);
        } else {
            throw new RuntimeException("Facture not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFacture(@PathVariable long id) {
        factureService.deleteFacture(id);
    }
}
