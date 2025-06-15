package ua.glek.crm_adv.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.glek.crm_adv.model.jpa.Category;
import ua.glek.crm_adv.service.admin.CategoryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void testSaveAndFindCategory() {
        Category category = new Category();
        category.setName("testCategory2");
        categoryService.addCategory(category);
       Category result =  categoryService.findCategoryByName("testCategory2");
        assertEquals("testCategory2", result.getName());


    }
    @Test
    void testDeleteCategory() {
        Category category = new Category();
        category.setName("testCategory2");
        Category result =  categoryService.findCategoryByName(category.getName());
        categoryService.deleteCategoryById(result.getId());
        Category deletedCategory = categoryService.findCategoryByName(category.getName());
        assertNull(deletedCategory);
    }
}
