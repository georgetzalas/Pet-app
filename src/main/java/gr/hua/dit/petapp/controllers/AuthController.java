package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.*;
import gr.hua.dit.petapp.payload.request.*;
import gr.hua.dit.petapp.payload.response.JwtResponse;
import gr.hua.dit.petapp.repositories.*;
import gr.hua.dit.petapp.services.EmailService;
import gr.hua.dit.petapp.services.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gr.hua.dit.petapp.payload.response.MessageResponse;
import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import gr.hua.dit.petapp.config.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    VetRepository vetRepository;
    ShelterRepository shelterRepository;
    CitizenRepository citizenRepository;
    AdminRepository adminRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder encoder;
    JwtUtils jwtUtils;
    EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder, JwtUtils jwtUtils, VetRepository vetRepository, ShelterRepository shelterRepository, CitizenRepository citizenRepository, AdminRepository adminRepository, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.vetRepository = vetRepository;
        this.shelterRepository = shelterRepository;
        this.citizenRepository = citizenRepository;
        this.adminRepository = adminRepository;
        this.emailService = emailService;
    }

    @PostConstruct
    public void setup() {
        Role role_user = new Role("ROLE_CITIZEN");
        Role role_admin = new Role("ROLE_ADMIN");
        Role role_vet = new Role("ROLE_VET");
        Role role_shelter = new Role("ROLE_SHELTER");

        roleRepository.updateOrInsert(role_user);
        roleRepository.updateOrInsert(role_admin);
        roleRepository.updateOrInsert(role_vet);
        roleRepository.updateOrInsert(role_shelter);

        if(!adminRepository.existsByUsername("admin"))
        {
            Admin admin = new Admin("admin", "admin", "admin@gmail.com", encoder.encode("admin"), "admin");
            Set<Role> roles = new HashSet<>();

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            admin.setRoles(roles);
            adminRepository.save(admin);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("authentication");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        System.out.println("authentication: " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("post authentication");
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("jwt: " + jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        String surname = null;
        String region  = null;

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if(user.isPresent())
        {
            if(user.get().getClass() == Citizen.class)
            {
                Citizen citizen = (Citizen) user.get();
                surname = citizen.getSurname();
            }
            if(user.get().getClass() == Shelter.class)
            {
                Shelter shelter = (Shelter) user.get();
                region = shelter.getRegion();
            }
            if(user.get().getClass() == Admin.class)
            {
                Admin admin = (Admin) user.get();
                surname = admin.getSurname();
            }
            if(user.get().getClass() == Vet.class)
            {
                Vet vet = (Vet) user.get();
                surname = vet.getSurname();
            }
        }

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                surname,
                region));
    }

    /*@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getName(),signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName("ROLE_CITIZEN")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "vet":
                        Role vetRole = roleRepository.findByName("ROLE_VET")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(vetRole);

                        break;

                    case "shelter":
                        Role shelterRole = roleRepository.findByName("ROLE_SHELTER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(shelterRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName("ROLE_CITIZEN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }*/

    @PostMapping("/signup/vet")
    public ResponseEntity<?> registerVet(@Valid @RequestBody SignupRequestVet signUpRequest)
    {
        if (vetRepository.existsByUsername(signUpRequest.getUsername())
                || shelterRepository.existsByUsername(signUpRequest.getUsername())
                || citizenRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (vetRepository.existsByEmail(signUpRequest.getEmail())
            || shelterRepository.existsByEmail(signUpRequest.getEmail())
            || citizenRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Vet vet = new Vet(signUpRequest.getName(),
                            signUpRequest.getUsername(),
                            signUpRequest.getEmail(),
                            encoder.encode(signUpRequest.getPassword()),
                            signUpRequest.getSurname());
        Set<Role> roles = new HashSet<>();

        Role vetRole = roleRepository.findByName("ROLE_VET")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(vetRole);

        vet.setRoles(roles);
        vetRepository.save(vet);
        emailService.sendWelcomeEmail(signUpRequest.getEmail());

        return ResponseEntity.ok(new MessageResponse("Vet registered successfully!"));
    }

    @PostMapping("/signup/shelter")
    public ResponseEntity<?> registerShelter(@Valid @RequestBody SignupRequestShelter signUpRequest)
    {
        if (vetRepository.existsByUsername(signUpRequest.getUsername())
                || shelterRepository.existsByUsername(signUpRequest.getUsername())
                || citizenRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (vetRepository.existsByEmail(signUpRequest.getEmail())
                || shelterRepository.existsByEmail(signUpRequest.getEmail())
                || citizenRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Shelter shelter = new Shelter(signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getRegion());
        Set<Role> roles = new HashSet<>();

        Role shelterRole = roleRepository.findByName("ROLE_SHELTER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(shelterRole);

        shelter.setRoles(roles);
        System.out.println(shelter);
        shelterRepository.save(shelter);
        emailService.sendWelcomeEmail(signUpRequest.getEmail());

        return ResponseEntity.ok(new MessageResponse("Shelter registered successfully!"));
    }

    @PostMapping("/signup/citizen")
    public ResponseEntity<?> registerCitizen(@Valid @RequestBody SignupRequestCitizen signUpRequest)
    {
        if (vetRepository.existsByUsername(signUpRequest.getUsername())
                || shelterRepository.existsByUsername(signUpRequest.getUsername())
                || citizenRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (vetRepository.existsByEmail(signUpRequest.getEmail())
                || shelterRepository.existsByEmail(signUpRequest.getEmail())
                || citizenRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Citizen citizen = new Citizen(signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getSurname());
        Set<Role> roles = new HashSet<>();

        Role citizenRole = roleRepository.findByName("ROLE_CITIZEN")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(citizenRole);

        citizen.setRoles(roles);
        citizenRepository.save(citizen);
        emailService.sendWelcomeEmail(signUpRequest.getEmail());

        return ResponseEntity.ok(new MessageResponse("Citizen registered successfully!"));
    }
}