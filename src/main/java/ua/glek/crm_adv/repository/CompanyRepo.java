package ua.glek.crm_adv.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.glek.crm_adv.model.Company;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    @EntityGraph(attributePaths = {"manager","manager.roles"})
    List<Company> findAll();
}
