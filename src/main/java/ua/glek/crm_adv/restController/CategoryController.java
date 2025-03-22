package ua.glek.crm_adv.restController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.Category;
import ua.glek.crm_adv.repository.CategoryRepo;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/moderator/category")
public class CategoryController {
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/all")
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @PostMapping("/add")
    private Category add(@RequestBody Category category) {
        log.info("add category: {}", category);
        return categoryRepo.save(category);
    }
    @PostMapping("/addMore")
    private List<Category> addMore(@RequestBody List<Category> category) {
        log.info("add more category: {}", category);
        return categoryRepo.saveAll(category);
    }
}
