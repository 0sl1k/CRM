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
import ua.glek.crm_adv.service.CategoryService;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/moderator/category")
@RequiredArgsConstructor
public class CategoryController {





    @Autowired
    private CategoryService categoryService;


    @GetMapping("/all")
    public List<Category> getAll() {
        return categoryService.getAll();
    }
    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        log.info("add category: {}", category);
        return categoryService.addCategory(category);
    }
    @PostMapping("/addMore")
    public List<Category> addMore(@RequestBody List<Category> category) {
        log.info("add more category: {}", category);
        return categoryService.addCategories(category);
    }
    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        return categoryService.getCategoryById(id).get();


    }
}
