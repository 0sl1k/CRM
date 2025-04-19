package ua.glek.crm_adv.repository.Jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.glek.crm_adv.model.jpa.ERole;
import ua.glek.crm_adv.model.jpa.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
