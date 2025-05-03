package ua.glek.crm_adv.restController.moderator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.jpa.EStatus;
import ua.glek.crm_adv.model.jpa.Order;
import ua.glek.crm_adv.service.moderator.ModeratorOrderService;

@RestController
@RequestMapping("/api/moderator/order")
public class ModeratorOrderController {
    @Autowired
    private ModeratorOrderService moderatorOrderService;

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("id") Long id,
                                                    @RequestParam EStatus status) {
        return ResponseEntity.ok(moderatorOrderService.updateOrderStatus(id, status));
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<Order> searchOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(moderatorOrderService.findOrderById(id));
    }
}
