package ua.glek.crm_adv.restController.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.jpa.EStatus;
import ua.glek.crm_adv.model.jpa.Order;
import ua.glek.crm_adv.service.admin.AdminOrderService;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    @Autowired
    private AdminOrderService adminOrderService;



    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
        return adminOrderService.createOrder(order);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("id") Long id,
                                               @RequestParam EStatus status) {
       return ResponseEntity.ok(adminOrderService.updateOrderStatus(id, status));
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<Order> searchOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminOrderService.findOrderById(id));
    }
    @DeleteMapping("/{id}/delete")
    public void deleteOrder(@PathVariable("id") Long id) {
        adminOrderService.deleteOrder(id);
    }
}
