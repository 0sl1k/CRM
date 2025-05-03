package ua.glek.crm_adv.service.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.glek.crm_adv.model.jpa.EStatus;
import ua.glek.crm_adv.model.jpa.Order;
import ua.glek.crm_adv.repository.Jpa.OrderRepo;

import java.util.Optional;

@Service
public class ModeratorOrderService {
    @Autowired
    private OrderRepo orderRepo;

    private final String HASH_NAME = "order";

    @Transactional
    @CacheEvict(value = HASH_NAME,allEntries = true)
    public String updateOrderStatus(Long orderId, EStatus status) {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            orderRepo.save(order);
            return "Order updated";
        }else {
            return "Order not found";
        }
    }
    public Order findOrderById(long id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.orElseThrow(()-> new RuntimeException("Order not found"));
    }
}
