package ua.glek.crm_adv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.model.elastic.ESProduct;
import ua.glek.crm_adv.model.jpa.Category;
import ua.glek.crm_adv.model.jpa.Product;
import ua.glek.crm_adv.model.jpa.User;
import ua.glek.crm_adv.repository.Jpa.ProductRepo;
import ua.glek.crm_adv.repository.elastic.ESProductRepo;

import java.time.Duration;
import java.util.List;
@Slf4j
@Service
public class ProductService {
    @Autowired
    private ESProductRepo esProductRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryService categoryService;



    private   ESProduct toESProduct(Product product) {
        ESProduct esProduct = new ESProduct();
        esProduct.setId(product.getId().toString()  );
        esProduct.setName(product.getName());
        esProduct.setDescription(product.getDescription());
        esProduct.setPrice(product.getPrice());
        esProduct.setCategory(product.getCategory());
//        esProduct.setAuthor(product.getAuthor());
        return esProduct;
    }

    public void save(UserDetailsImpl userDetails,Product product) {
        User author = userDetails.getUser();
        product.setAuthor(author);
        Product savedProduct = productRepo.save(product);
        Product loaded = productRepo.findById(savedProduct.getId()).orElseThrow(() -> new RuntimeException("Not found"));
        ESProduct esProduct = toESProduct(loaded);
        esProductRepo.save(esProduct);


    }
    public Iterable<ESProduct> findAll(){
        return esProductRepo.findAll();
    }

    public Iterable<ESProduct> findByNameContains(String nameContains) {
        return esProductRepo.findByNameContaining(nameContains);
    }
    public Iterable<ESProduct> findByCategory(String categoryName) {
        return esProductRepo.findByCategoryName(categoryName);
    }
    @Scheduled(fixedRate = 60000)
    public void SyncData(){
        esProductRepo.deleteAll();
        List<Product> products = productRepo.findAll();
        List<ESProduct> elasticList = products.stream()
                .map(this::toESProduct)
                .toList();
        esProductRepo.saveAll(elasticList);
        log.info("Product data sync completed");
    }
}
