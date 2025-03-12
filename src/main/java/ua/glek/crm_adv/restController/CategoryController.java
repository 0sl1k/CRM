package ua.glek.crm_adv.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.Category;
import ua.glek.crm_adv.repository.CategoryRepo;

import java.util.List;

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
        return categoryRepo.save(category);
    }
}
