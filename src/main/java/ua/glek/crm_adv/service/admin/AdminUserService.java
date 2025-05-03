package ua.glek.crm_adv.service.admin;

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
import ua.glek.crm_adv.model.jpa.Role;
import ua.glek.crm_adv.model.jpa.User;
import ua.glek.crm_adv.repository.Jpa.RoleRepo;
import ua.glek.crm_adv.repository.Jpa.UserRepo;
import ua.glek.crm_adv.service.UserDetailsImpl;

import java.time.LocalDateTime;

@Service
public class AdminUserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void BanUser(Long id, LocalDateTime until){
        User user = userRepo.findById(id).get();

        user.setBanned(true);
        user.setBanEndDate(until);
        userRepo.save(user);
    }
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



    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
    public void deleteUser(UserDetails userDetails) {
        User user = findByUsername(userDetails.getUsername());
        userRepo.deleteById(user.getId());

    }

    public void assignRoleToUser(Long userid, Long roleId) {
        User user = userRepo.findById(userid)
                .orElseThrow(() -> new UsernameNotFoundException(userid.toString()));

        Role role = roleRepo.findById(roleId)
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

        return new UserDetailsImpl(user);
    }
}
