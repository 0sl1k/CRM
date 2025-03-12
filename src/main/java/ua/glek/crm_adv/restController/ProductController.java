package ua.glek.crm_adv.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.Product;
import ua.glek.crm_adv.repository.ProductRepo;

import java.util.List;

@RestController
@RequestMapping("api/moderator/product")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/all")
    public List<Product> all(){
        return productRepo.findAll();
    }

    @PostMapping("/add")
    public Product add(@RequestBody Product product) {
        return productRepo.save(product);
    }
}
