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
public class ClientService {

    @Autowired
    private UserRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createClient(User client) {
        return clientRepository.save(client);
    }

    public List<User> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<User> getClientById(long id) {
        return clientRepository.findById(id);
    }
    public List<User> getUsersWithRoleOwner() {
        return userRepository.findByRoleName(ERole.ROLE_USER);
    }
    @Transactional
    public User updateClient(User client) {
        return clientRepository.save(client);
    }

    @Transactional
    public void deleteClient(long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Client not found with id: " + id);
        }
    }
}
