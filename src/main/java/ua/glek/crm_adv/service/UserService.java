package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.glek.crm_adv.dto.ChangePasswordDto;
import ua.glek.crm_adv.model.ERole;
import ua.glek.crm_adv.model.Role;
import ua.glek.crm_adv.model.User;
import ua.glek.crm_adv.repository.RoleRepo;
import ua.glek.crm_adv.repository.UserRepo;



@Service
public class UserService  implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void changePassword(ChangePasswordDto changePasswordDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong old password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepo.save(user);

    }

    public void assignRoleToUser(Long userid, Long roleid) {
        User user = userRepo.findById(userid)
                .orElseThrow(() -> new UsernameNotFoundException(userid.toString()));

        Role role = roleRepo.findById(roleid)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        if(user.getRoles().contains(role)) {
            throw new RuntimeException("Role is already assigned");
        }
        user.getRoles().add(role);
        userRepo.save(user);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {

            String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();


            return userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        }

        throw new RuntimeException("No authenticated user found");
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User nor found with username" + username));

        return UserDetailsImpl.build(user);
    }
}
