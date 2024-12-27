package com.example.spring_security_jwt.Service;


import com.example.spring_security_jwt.Entity.ServiceEntity;
import com.example.spring_security_jwt.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Transactional
    public ServiceEntity createService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<ServiceEntity> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    @Transactional
    public ServiceEntity updateService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Service not found with id: " + id);
        }
    }
}
