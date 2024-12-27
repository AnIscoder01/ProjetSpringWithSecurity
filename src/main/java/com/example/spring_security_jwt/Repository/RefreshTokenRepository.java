package com.example.spring_security_jwt.Repository;

import com.example.spring_security_jwt.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token); // This should find the token, not delete it
}