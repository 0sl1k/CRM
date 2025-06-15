package ua.glek.crm_adv.restController.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.jpa.Category;
import ua.glek.crm_adv.service.admin.CategoryService;

import java.util.List;

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
        return categoryService.getCategoryById(id).orElseThrow(()-> new RuntimeException("Category not found"));


    }
}
