package com.example.spring_security_jwt.Repository;

import com.example.spring_security_jwt.Entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {
}
