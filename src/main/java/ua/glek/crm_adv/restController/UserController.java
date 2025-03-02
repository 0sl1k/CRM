package ua.glek.crm_adv.restController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.dto.AssignRoleDto;
import ua.glek.crm_adv.dto.ChangePasswordDto;
import ua.glek.crm_adv.model.Role;
import ua.glek.crm_adv.model.User;
import ua.glek.crm_adv.service.UserDetailsImpl;
import ua.glek.crm_adv.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
        public ResponseEntity<?> getInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        userService.changePassword(changePasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/assignRole")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        userService.assignRoleToUser(assignRoleDto.getUserid(), assignRoleDto.getRoleid());
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
