package ua.glek.crm_adv.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.glek.crm_adv.model.ERole;
import ua.glek.crm_adv.model.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
