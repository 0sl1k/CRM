package ua.glek.crm_adv.restController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.dto.AssignRoleDto;
import ua.glek.crm_adv.dto.ChangePasswordDto;
import ua.glek.crm_adv.response.UserInfoResponse;
import ua.glek.crm_adv.service.UserDetailsImpl;
import ua.glek.crm_adv.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/info")
        public ResponseEntity<?> getInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new UserInfoResponse(userDetails.getId()
                ,userDetails.getUsername()
                ,userDetails.getEmail())
                ,HttpStatus.OK);

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
