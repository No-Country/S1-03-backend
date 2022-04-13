package com.nocountry.messenger.controller;

import com.nocountry.messenger.dto.request.ClientRequest;
import com.nocountry.messenger.dto.request.LoginRequest;
import com.nocountry.messenger.dto.response.JwtResponse;
import com.nocountry.messenger.dto.response.MessageResponse;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.Role;
import com.nocountry.messenger.model.entity.ERole;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.repository.IRoleRepository;
import com.nocountry.messenger.security.UserDetailsImpl;
import com.nocountry.messenger.security.jwt.JwtUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin    //ver si es necesario
public class ClientController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IClientRepository clientRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ClientRequest signUpRequest) {
        
        if (clientRepository.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        
        if (clientRepository.existsByMail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        
        // Create new user's account
        Client user = Client.builder()
                .name(signUpRequest.getName())
                .lastName(signUpRequest.getLastName())
                .userName(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .mail(signUpRequest.getEmail())
                .document(signUpRequest.getDocument())
                .birthdate(string2LocalDate(signUpRequest.getBirthdate()))
                .address(signUpRequest.getAddress())
                .phoneNumber(signUpRequest.getPhoneNumber())
                //AGREGAR PROFILE IMAGE ACÁ
                .build();
        
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        clientRepository.save(user);
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }
}
