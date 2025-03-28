package ua.glek.crm_adv.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.glek.crm_adv.model.Category;
import ua.glek.crm_adv.repository.CategoryRepo;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/moderator/category")
@RequiredArgsConstructor
public class CategoryController {


   private static RedisTemplate<String,Category> redisTemplate;
   private static final String HASH_NAME = "category";

    @Autowired
    private CategoryRepo categoryRepo;


    @GetMapping("/all")
    @Cacheable(value = HASH_NAME)
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }
    @CacheEvict(value = HASH_NAME,key = "#category.id",allEntries = true)
    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        log.info("add category: {}", category);
        return categoryRepo.save(category);
    }
    @CacheEvict(value = HASH_NAME,allEntries = true)
    @PostMapping("/addMore")
    public List<Category> addMore(@RequestBody List<Category> category) {
        log.info("add more category: {}", category);
        return categoryRepo.saveAll(category);
    }
    @Cacheable(value = HASH_NAME,key = "#id")
    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        return categoryRepo.findById(id).get();


    }
}
