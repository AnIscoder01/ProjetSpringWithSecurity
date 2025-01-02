package com.example.spring_security_jwt.RestController;


import com.example.spring_security_jwt.Entity.User;
import com.example.spring_security_jwt.Repository.UserRepository;
import com.example.spring_security_jwt.Service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/owner")
public class OwnerRestController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public User createOwner(@RequestBody User owner) {
        return ownerService.createOwner(owner);
    }

    @GetMapping("/all")
    public List<User> getOwners() {
        List<User> owners = ownerService.getUsersWithRoleOwner();
        return owners;
    }

    @GetMapping("/getOne/{id}")
    public User getOwnerById(@PathVariable long id) {
        return ownerService.getOwnerById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public User updateOwner(@PathVariable long id, @RequestBody User owner) {
        User existingOwner = ownerService.getOwnerById(id).orElse(null);

        if (existingOwner != null) {
            owner.setId(id);
            return ownerService.updateOwner(owner);
        } else {
            throw new RuntimeException("Owner not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOwner(@PathVariable long id) {
        ownerService.deleteOwner(id);
    }
}
