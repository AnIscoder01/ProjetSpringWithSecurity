package com.example.spring_security_jwt.RestController;


import com.example.spring_security_jwt.Entity.User;
import com.example.spring_security_jwt.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/save")
    public User createAdmin(@RequestBody User admin) {
        return adminService.createAdmin(admin);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllAdmins() {
        return adminService.getUsersWithRoleOwner();
    }

    @GetMapping("/getOne/{id}")
    public User getAdminById(@PathVariable long id) {
        return adminService.getAdminById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public User updateAdmin(@PathVariable long id, @RequestBody User admin) {
        User existingAdmin = adminService.getAdminById(id).orElse(null);

        if (existingAdmin != null) {
            admin.setId(id);
            return adminService.updateAdmin(admin);
        } else {
            throw new RuntimeException("Admin not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAdmin(@PathVariable long id) {
        adminService.deleteAdmin(id);
    }
}
