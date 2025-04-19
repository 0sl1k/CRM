package ua.glek.crm_adv.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.jpa.EStatus;
import ua.glek.crm_adv.model.jpa.Order;
import ua.glek.crm_adv.service.OrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {


    @Autowired
    private OrderService orderService;



    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
       return orderService.createOrder(order);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("id") Long id,
                                               @RequestParam EStatus status) {
       return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
