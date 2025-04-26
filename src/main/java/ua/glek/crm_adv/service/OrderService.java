package ua.glek.crm_adv.service;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;



    private final String HASH_NAME = "order";

    @Transactional
    @CacheEvict(value = HASH_NAME,key = "order.id",allEntries = true)
    public Order createOrder(Order order) {
       String username =SecurityContextHolder.getContext().getAuthentication().getName();
       User customer = userRepo.findByUsername(username).orElse(null);

       order.setCustomer(customer);
       order.setStatus(EStatus.NEW);

       List<OrderProducts> products = new ArrayList<>();
        double totalOrderPrice = 0.0;

       for (OrderProducts items : order.getProductsList()){
           Product product = productRepo.findById(items.getProduct().getId()).get();
            int quantity = items.getQuantity();
            double unitPrice = product.getPrice();

           items.setProduct(product);
           items.setOrder(order);

           double discountedUnitPrice = unitPrice * (1- product.getBulkDiscountPrice()/100.0);
           double totalPrice = discountedUnitPrice *quantity;
           totalOrderPrice += totalPrice;

           items.setPrice(discountedUnitPrice);
           products.add(items);


       }
        order.setTotalPrice(totalOrderPrice);
        order.setProductsList(products);


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
