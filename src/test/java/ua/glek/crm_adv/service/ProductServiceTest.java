package ua.glek.crm_adv.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import ua.glek.crm_adv.model.jpa.Product;
import ua.glek.crm_adv.model.jpa.User;
import ua.glek.crm_adv.repository.Jpa.ProductRepo;
import ua.glek.crm_adv.repository.Jpa.UserRepo;
import ua.glek.crm_adv.repository.elastic.ESProductRepo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;

    private ESProductRepo esProductRepo;
    @Autowired
    private UserRepo userRepo;

    private UserDetailsImpl testUserDetails;

    @BeforeEach
    public void setUp() {
        userRepo.findByEmail("testUser@gmail.com").ifPresent(userRepo::delete);
        productRepo.findByName("testProduct").ifPresent(productRepo::delete);

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("testUser@gmail.com");
        user.setPassword("testPassword");
        userRepo.save(user);

        testUserDetails = new UserDetailsImpl(user);
    }

    @Test
    void SaveProduct(){
        Product product = new Product();
        product.setName("testProduct");
        product.setPrice(100000000.0);
        productService.save(testUserDetails,product);

        List<Product> products = productRepo.findAll();

        Optional<Product> found = products.stream().filter(p -> p.getName().equals("testProduct")).findFirst();


        assertEquals("testUser",found.get().getAuthor().getUsername());
        assertEquals("testUser@gmail.com",found.get().getAuthor().getEmail());


    }

    @Test
    void deleteProduct(){
        Product product = new Product();
        product.setName("testProduct");
        product.setPrice(100000000.0);
        productService.save(testUserDetails,product);



        Optional<Product> found = Optional.ofNullable(
                productRepo.findByName("testProduct")
                        .orElseThrow(() -> new RuntimeException("Product not found")));

        productRepo.delete(found.get());

        boolean exist =  productRepo.existsById(found.get().getId());
        assertEquals(exist,false);

    }




}
