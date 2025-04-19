package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.glek.crm_adv.model.jpa.*;
import ua.glek.crm_adv.repository.Jpa.OrderRepo;
import ua.glek.crm_adv.repository.Jpa.ProductRepo;
import ua.glek.crm_adv.repository.Jpa.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;

    private EStatus status;

    private final String HASH_NAME = "order";

    @Transactional
    @CacheEvict(value = HASH_NAME,key = "order.id",allEntries = true)
    public Order createOrder(Order order) {
       String username =SecurityContextHolder.getContext().getAuthentication().getName();
       User customer = userRepo.findByUsername(username).orElse(null);

       order.setCustomer(customer);
       order.setStatus(EStatus.NEW);

       List<OrderProducts> products = new ArrayList<>();

       for (OrderProducts items : order.getProductsList()){
           Product product = productRepo.findById(items.getProduct().getId()).orElse(null);
           items.setProduct(product);
           items.setOrder(order);
           products.add(items);
       }
       order.setProductsList(products);
       double totalPrice = products.stream()
               .mapToDouble(OrderProducts::getTotalPrice)
               .sum();

       order.setTotalPrice(totalPrice);
       return orderRepo.save(order);
    }


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
}
