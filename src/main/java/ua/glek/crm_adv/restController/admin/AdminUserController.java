package ua.glek.crm_adv.restController.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.dto.AssignRoleDto;
import ua.glek.crm_adv.dto.ChangePasswordDto;
import ua.glek.crm_adv.request.BanRequest;
import ua.glek.crm_adv.response.UserInfoResponse;
import ua.glek.crm_adv.service.admin.AdminUserService;
import ua.glek.crm_adv.service.UserDetailsImpl;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;



    @PostMapping("/{id}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long id, @RequestBody BanRequest banRequest) {
        adminUserService.BanUser(id,banRequest.getUntilTime());
        return  new ResponseEntity<>("Ban successful", HttpStatus.OK);
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
        adminUserService.changePassword(changePasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/assignRole")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleDto assignRoleDto) {
       adminUserService.assignRoleToUser(assignRoleDto.getUserid(), assignRoleDto.getRoleid());
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping("/secure-endpoint")
    public ResponseEntity<?> getSecureEndpoint(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(!userDetails.isAccountNonLocked()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Your account is locked to" + userDetails.getUser().getBanEndDate());
        }
        return ResponseEntity.ok("Your are Welcome");
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminUserService.deleteUser(userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
