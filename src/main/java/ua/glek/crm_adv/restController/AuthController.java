package ua.glek.crm_adv.restController;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.glek.crm_adv.dto.LogInDto;
import ua.glek.crm_adv.dto.SignUpDto;
import ua.glek.crm_adv.jwt.JwtUtils;
import ua.glek.crm_adv.service.AuthService;
import ua.glek.crm_adv.service.UserService;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LogInDto sign, HttpServletResponse response){
        log.info("User logged in");
        return authService.login(sign, response);


    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody SignUpDto sign){
        log.info("User registered");
        return authService.registerUser(sign);
    }
    @PostMapping("/signOut")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        Cookie cookie = jwtUtils.getCleanJwtCookie();
        response.addCookie(cookie);
        log.info("User logged out");
        return ResponseEntity.ok("Logout successful");
    }

}
