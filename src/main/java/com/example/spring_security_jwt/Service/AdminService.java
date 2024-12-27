package com.example.spring_security_jwt.Service;

import com.example.spring_security_jwt.Entity.ERole;
import com.example.spring_security_jwt.Entity.User;
import com.example.spring_security_jwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository adminRepository;
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersWithRoleOwner() {
        return userRepository.findByRoleName(ERole.ROLE_ADMIN);
    }

    @Transactional
    public User createAdmin(User admin) {
        return adminRepository.save(admin);
    }

    public List<User> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<User> getAdminById(long id) {
        return adminRepository.findById(id);
    }

    @Transactional
    public User updateAdmin(User admin) {
        return adminRepository.save(admin);
    }

    @Transactional
    public void deleteAdmin(long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new RuntimeException("Admin not found with id: " + id);
        }
    }
}
