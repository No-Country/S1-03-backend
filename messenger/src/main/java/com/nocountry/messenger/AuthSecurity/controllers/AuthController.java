package com.nocountry.messenger.AuthSecurity.controllers;

import com.nocountry.messenger.AuthSecurity.payload.request.LoginRequest;
import com.nocountry.messenger.AuthSecurity.payload.request.SignupRequest;
import com.nocountry.messenger.AuthSecurity.payload.response.JwtResponse;
import com.nocountry.messenger.AuthSecurity.payload.response.MessageResponse;
import com.nocountry.messenger.AuthSecurity.security.jwt.JwtUtils;
import com.nocountry.messenger.AuthSecurity.security.services.UserDetailsImpl;
import com.nocountry.messenger.model.entity.Cliente;
import com.nocountry.messenger.model.entity.Role;
import com.nocountry.messenger.model.entity.RoleType;
import com.nocountry.messenger.repository.IClientRepository;
import com.nocountry.messenger.repository.RoleRepository;
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
	IClientRepository clienteRepository;
	@Autowired
	RoleRepository roleRepository;
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
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (clienteRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		if (clienteRepository.existsByMail(signUpRequest.getMail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		Cliente cliente = new Cliente(signUpRequest.getUsername(), 
							 signUpRequest.getMail(),
							 encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleType.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleType.ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				
				default:
					Role userRole = roleRepository.findByName(RoleType.USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		cliente.setRole((Role) (Set<Role>) roles);
		clienteRepository.save(cliente);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
        
        @PutMapping("/modificar")
    public ResponseEntity<Object> moddificarUser(@PathVariable String mail, 
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