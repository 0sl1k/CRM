package ua.glek.crm_adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
