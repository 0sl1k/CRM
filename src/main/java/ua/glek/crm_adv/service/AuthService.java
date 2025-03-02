package ua.glek.crm_adv.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.dto.LogInDto;
import ua.glek.crm_adv.dto.SignUpDto;
import ua.glek.crm_adv.jwt.JwtUtils;
import ua.glek.crm_adv.model.ERole;
import ua.glek.crm_adv.model.Role;
import ua.glek.crm_adv.model.User;
import ua.glek.crm_adv.repository.RoleRepo;
import ua.glek.crm_adv.repository.UserRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    public void login(LogInDto sign, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(sign.getUsername(),sign.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Cookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);


        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        response.addCookie(jwtCookie);
        ResponseEntity.ok("Login is successful");
    }

    public void registerUser(SignUpDto sign) {
        User user = new User();
        user.setUsername(sign.getUsername());
        if (userRepo.existsUserByEmail(sign.getEmail())){
            new ResponseEntity<>("Error:Account with this email already exists", HttpStatus.BAD_REQUEST);
            return;
        }else {
            user.setEmail(sign.getEmail());
        }
        if (sign.getPassword().length()<8){
            new ResponseEntity<>("Error: This password short", HttpStatus.BAD_REQUEST);
            return;
        }else {
            user.setPassword(bCryptPasswordEncoder.encode(sign.getPassword()));
        }
        Set<String> strRoles = sign.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepo.findByName((ERole.ROLE_USER))
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepo.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepo.save(user);
        new ResponseEntity<>("register sucesful", HttpStatus.OK);
    }

}
