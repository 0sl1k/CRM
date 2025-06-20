package ua.glek.crm_adv.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.model.jpa.Category;
import ua.glek.crm_adv.repository.Jpa.CategoryRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private static final String HASH_NAME = "category";

    @Autowired
    private CategoryRepo categoryRepo;

    @Cacheable(value = HASH_NAME)
    public List<Category> getAll(){
        return categoryRepo.findAll();
    }
    @CacheEvict(value = HASH_NAME, allEntries = true)
    public Category addCategory(Category category){
        return categoryRepo.save(category);
    }
    @CacheEvict(value = HASH_NAME, allEntries = true)
    public List<Category> addCategories(List<Category> categories){
        return categoryRepo.saveAll(categories);
    }
    @Cacheable(value = HASH_NAME,key = "#id")
    public Optional<Category> getCategoryById(Long id){
        return categoryRepo.findById(id);
    }
    public Category findCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }
    public void deleteCategoryById(Long id){
        Optional<Category> category = categoryRepo.findById(id);
        categoryRepo.delete(category.orElseThrow(()-> new RuntimeException("Category not found")));

    }

}
