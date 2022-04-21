package com.nocountry.messenger.controller;

import com.nocountry.messenger.model.entity.ERole;
import com.nocountry.messenger.model.entity.Role;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.repository.IRoleRepository;
import com.nocountry.messenger.security.jwt.JwtUtils;
import com.nocountry.messenger.security.UserDetailsImpl;
import com.nocountry.messenger.dto.request.SignupRequest;
import com.nocountry.messenger.dto.request.LoginRequest;
import com.nocountry.messenger.dto.response.MessageResponse;
import com.nocountry.messenger.dto.response.JwtResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
	@Autowired
	AuthenticationManager authenticationManager;
        
	@Autowired
        private IClientRepository clientRepository;
        
	@Autowired
	private IRoleRepository roleRepository;
        
	@Autowired
	PasswordEncoder encoder;
        
	@Autowired
	private JwtUtils jwtUtils;
        
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = (Authentication) authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication((org.springframework.security.core.Authentication) authentication);
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
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (clientRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		if (clientRepository.existsByMail(signUpRequest.getMail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		Client client = new Client();
                
                client.setName(signUpRequest.getName());
                client.setLastName(signUpRequest.getLastName());
                client.setUserName(signUpRequest.getUsername());
                client.setPhoneNumber(signUpRequest.getPhoneNumber());
                client.setDocument(signUpRequest.getDocument());
                client.setAddress(signUpRequest.getAdress());
                client.setMail(signUpRequest.getMail());
                client.setPassword(signUpRequest.getPassword());
                
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
		client.setRoles(roles);
		clientRepository.save(client);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

    @PutMapping("/modificar")
    public ResponseEntity<Object> moddificarUser(@PathVariable String email, 
            @RequestParam(value = "name", required = false) String name, 
            @RequestParam(value = "username", required = false) String username) {
        System.out.println("username = " + username);
        return new ResponseEntity<>(username + " " + name + " " + username, HttpStatus.OK);

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("idUser");
        return "redict:/";
    }

	private LocalDate string2LocalDate (String stringDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate date = LocalDate.parse(stringDate, formatter);
		return date;
	}
}