package ua.glek.crm_adv.repository.Jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.jpa.Category;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);

}
