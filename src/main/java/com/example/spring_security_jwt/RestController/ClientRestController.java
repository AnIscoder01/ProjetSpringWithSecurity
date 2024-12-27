package com.example.spring_security_jwt.RestController;

import com.example.spring_security_jwt.Entity.User;
import com.example.spring_security_jwt.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/save")
    public User createClient(@RequestBody User client) {
        return clientService.createClient(client);
    }

    @GetMapping("/all")
    public List<User> getAllClients() {
        return clientService.getUsersWithRoleOwner();
    }

    @GetMapping("/getOne/{id}")
    public User getClientById(@PathVariable long id) {
        return clientService.getClientById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public User updateClient(@PathVariable long id, @RequestBody User client) {
        User existingClient = clientService.getClientById(id).orElse(null);

        if (existingClient != null) {
            client.setId(id);
            return clientService.updateClient(client);
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
    }
}
