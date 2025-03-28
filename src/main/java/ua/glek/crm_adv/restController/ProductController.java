package ua.glek.crm_adv.restController;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.Product;
import ua.glek.crm_adv.repository.ProductRepo;

import java.util.List;

@RestController
@RequestMapping("api/moderator/product")
public class ProductController {
    private static final String HASH_NAME = "product";
    @Autowired
    private ProductRepo productRepo;
    @Cacheable(value = HASH_NAME)
    @GetMapping("/all")
    public List<Product> all(){
        return productRepo.findAll();
    }
    @CacheEvict(value = HASH_NAME,key = "#product.id")
    @PostMapping("/add")
    public Product add(@RequestBody Product product) {
        return productRepo.save(product);
    }
}
