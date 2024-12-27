package com.example.spring_security_jwt.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min=3,max=20)
    private String username;

    @NotBlank
    @Size(min=3,max=20)
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6,max=40)
    private String password;

    @NotBlank
    @Size(min = 6,max=40)
    private String adresse;




}
