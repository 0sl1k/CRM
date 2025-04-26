package ua.glek.crm_adv.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.request.BanRequest;
import ua.glek.crm_adv.service.AdminUserService;

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
}
