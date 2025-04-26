package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.model.jpa.User;
import ua.glek.crm_adv.repository.Jpa.UserRepo;

import java.time.LocalDateTime;

@Service
public class AdminUserService {
    @Autowired
    private UserRepo userRepo;

    public void BanUser(Long id, LocalDateTime until){
        User user = userRepo.findById(id).get();

        user.setBanned(true);
        user.setBanEndDate(until);
        userRepo.save(user);
    }
}
