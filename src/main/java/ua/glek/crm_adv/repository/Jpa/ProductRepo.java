package ua.glek.crm_adv.repository.Jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.jpa.Product;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    Optional<Product> findWithCategoryById(Long id);

    Optional<Product> findByName(String name);
}
