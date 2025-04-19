package ua.glek.crm_adv.repository.Jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.glek.crm_adv.model.jpa.Company;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    @EntityGraph(attributePaths = {"manager","manager.roles"})
    List<Company> findAll();
}
