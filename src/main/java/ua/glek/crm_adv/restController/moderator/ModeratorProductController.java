package ua.glek.crm_adv.restController.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.elastic.ESProduct;
import ua.glek.crm_adv.model.jpa.Product;
import ua.glek.crm_adv.service.ProductService;

@RestController
@RequestMapping("api/moderator/product")
public class ModeratorProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public Iterable<ESProduct> all(){
        return productService.findAll();
    }


    @GetMapping("/search/{nameContains}")
    public Iterable<ESProduct> search(@PathVariable String nameContains) {
        return productService.findByNameContains(nameContains);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody Product product) {
        productService.updateProduct(id,product);
    }

}
