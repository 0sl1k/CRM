package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.glek.crm_adv.model.Order;
import ua.glek.crm_adv.model.Product;
import ua.glek.crm_adv.model.User;
import ua.glek.crm_adv.repository.OrderRepo;
import ua.glek.crm_adv.repository.ProductRepo;
import ua.glek.crm_adv.repository.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;

    @Transactional
    public Order createOrder(Order order) {
       String username =  SecurityContextHolder.getContext().getAuthentication().getName();
       User customer = userRepo.findByUsername(username)
               .orElseThrow(()->new RuntimeException("User not found"));
       order.setCustomer(customer);
        List<Product> products = order.getProducts().stream()
                .map(product ->productRepo.findById(product.getId())
                        .orElseThrow(()-> new RuntimeException("Product Not Found"+product.getId())))
                .collect(Collectors.toList());

        order.setProducts(products);
       return orderRepo.save(order);


    }
}
