package ua.glek.crm_adv.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.EStatus;
import ua.glek.crm_adv.model.Order;
import ua.glek.crm_adv.service.OrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private static final String HASH_NAME = "order";

    @Autowired
    private OrderService orderService;


    @CacheEvict(value = "order",key = "#order.id")
    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
       return orderService.createOrder(order);
    }

    @CachePut(value = HASH_NAME ,key = "#id")
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("id") Long id,
                                               @RequestParam EStatus status) {
       return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
