package ua.glek.crm_adv.repository.Jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.jpa.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

}
