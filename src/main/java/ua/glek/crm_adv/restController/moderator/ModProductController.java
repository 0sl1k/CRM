package ua.glek.crm_adv.restController.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.elastic.ESProduct;
import ua.glek.crm_adv.model.jpa.Product;
import ua.glek.crm_adv.service.ProductService;
import ua.glek.crm_adv.service.UserDetailsImpl;

@RestController
@RequestMapping("api/moderator/product")
public class ModProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public Iterable<ESProduct> all(){
        return productService.findAll();
    }

    @PostMapping("/add")
    public void add(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Product product) {
        productService.save(userDetails, product);
    }
    @GetMapping("/search/{nameContains}")
    public Iterable<ESProduct> search(@PathVariable String nameContains) {
        return productService.findByNameContains(nameContains);
    }
}
