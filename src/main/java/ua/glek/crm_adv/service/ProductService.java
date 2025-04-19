package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.model.elastic.ESProduct;
import ua.glek.crm_adv.model.jpa.Product;
import ua.glek.crm_adv.repository.Jpa.ProductRepo;
import ua.glek.crm_adv.repository.elastic.ESProductRepo;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ESProductRepo esProductRepo;
    @Autowired
    private ProductRepo productRepo;


    public static ESProduct toESProduct(Product product) {
        ESProduct esProduct = new ESProduct();
        esProduct.setId(product.getId().toString()  );
        esProduct.setName(product.getName());
        esProduct.setDescription(product.getDescription());
        esProduct.setPrice(product.getPrice());
        esProduct.setCategory(product.getCategory().getName());
        return esProduct;
    }

    public void save(Product product) {
        ESProduct esProduct = toESProduct(productRepo.save(product));
        esProductRepo.save(esProduct);


    }
    public Iterable<ESProduct> findAll(){
        return esProductRepo.findAll();
    }

    public Iterable<ESProduct> findByNameContains(String nameContains) {
        return esProductRepo.findByNameContaining(nameContains);
    }
}
