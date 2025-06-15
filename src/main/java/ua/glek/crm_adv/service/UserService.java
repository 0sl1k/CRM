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

import ua.glek.crm_adv.model.jpa.User;
import ua.glek.crm_adv.repository.Jpa.RoleRepo;
import ua.glek.crm_adv.repository.Jpa.UserRepo;



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


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User nor found with username" + username));

        return  UserDetailsImpl.build(user);
    }
}
