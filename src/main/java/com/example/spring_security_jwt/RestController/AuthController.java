package com.example.spring_security_jwt.RestController;
import com.example.spring_security_jwt.Entity.ERole;
import com.example.spring_security_jwt.Entity.RefreshToken;
import com.example.spring_security_jwt.Entity.Role;
import com.example.spring_security_jwt.Entity.User;
import com.example.spring_security_jwt.JWT.JwtUtils;
import com.example.spring_security_jwt.Repository.RoleRepository;
import com.example.spring_security_jwt.Repository.UserRepository;
import com.example.spring_security_jwt.Request.JwtResponse;
import com.example.spring_security_jwt.Request.LoginRequest;
import com.example.spring_security_jwt.Request.MessageResponse;
import com.example.spring_security_jwt.Request.SignupRequest;
import com.example.spring_security_jwt.Service.RefreshTokenService;
import com.example.spring_security_jwt.Service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        // Check if the username or email already exists in the database
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getAdresse());

        Set<String> strRoles = signupRequest.getRoles(); // Get roles from request
        System.out.println(signupRequest.getRoles());
        Set<Role> roles = new HashSet<>();

        // If no roles are provided, assign the "USER" role by default
        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found"));
            roles.add(userRole);
        } else {
            System.out.println(strRoles);
            // Assign roles based on the provided list
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(adminRole);
                        break;
                    case "OWNER":
                        Role ownerRole = roleRepository.findByName(ERole.ROLE_OWNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(ownerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(userRole);
                }
            });
        }

        // Assign roles to the user
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping(value="/signin",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
            Authentication authentication =authenticationManager
                    .authenticate(  new UsernamePasswordAuthenticationToken
                            (loginRequest.getUsername(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails.getAuthorities());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getAuthorities());

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles=userDetails.getAuthorities()
                .stream().map(item->item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(roles);
        RefreshToken refreshToken=refreshTokenService.createRefreshToken
                (userDetails.getId());
        System.out.println(refreshToken);

        return ResponseEntity.ok(new JwtResponse(jwt,refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),userDetails.getEmail(),roles));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        boolean isDeleted = refreshTokenService.deleteByToken(refreshToken);
        if (isDeleted) {
            return ResponseEntity.ok(new MessageResponse("Logout successful!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid refresh token!"));
        }
    }
}
