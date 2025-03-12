package ua.glek.crm_adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.Order;
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
