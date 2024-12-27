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
public class OwnerService {

    @Autowired
    private UserRepository ownerRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createOwner(User owner) {
        return ownerRepository.save(owner);
    }

    public List<User> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Optional<User> getOwnerById(long id) {
        return ownerRepository.findById(id);
    }
    public List<User> getUsersWithRoleOwner() {
        return userRepository.findByRoleName(ERole.ROLE_OWNER);
    }

    @Transactional
    public User updateOwner(User owner) {
        return ownerRepository.save(owner);
    }

    @Transactional
    public void deleteOwner(long id) {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Owner not found with id: " + id);
        }
    }
}
