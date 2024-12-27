package com.example.spring_security_jwt.JWT;

import com.example.spring_security_jwt.Service.UserDetailsImpl;
import io.jsonwebtoken.*;


import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Getter
@Setter
@Component
public class JwtUtils {
    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);

    @Value("${anis.app.jwtSecret}")
    private String JwtSecret;

    @Value("${anis.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    public String generateJwtToken(UserDetailsImpl userPrincipal){
        return generateTokenForUsername(userPrincipal.getUsername());
    }


    public String generateTokenForUsername(String username){
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs)).signWith(SignatureAlgorithm.HS512,JwtSecret)
                .compact();
    }
    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(JwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(JwtSecret).parseClaimsJws(authToken);
            return true;
        }
        catch(SignatureException e){
            logger.error("Invalid JWT signature :{}",e.getMessage());
        }
        catch(MalformedJwtException e){
            logger.error("Invalid JWT signature :{}",e.getMessage());
        }
        catch(ExpiredJwtException e){
            logger.error("Invalid JWT signature :{}",e.getMessage());
        }
        catch (UnsupportedJwtException e){
            logger.error("Invalid JWT signature :{}",e.getMessage());
        }
        catch (IllegalArgumentException e){
            logger.error("Invalid JWT signature :{}",e.getMessage());
        }
        return false;
    }
}
