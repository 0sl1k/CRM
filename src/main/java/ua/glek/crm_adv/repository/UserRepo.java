package ua.glek.crm_adv.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);
}
