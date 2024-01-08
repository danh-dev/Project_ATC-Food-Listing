package vn.hdweb.team9.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Category;
import vn.hdweb.team9.domain.entity.Food;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired private CategoryService categoryService;
    
    @Test
    void saveCategory() {
        // given
        Category category = new Category();
        category.setCategoryName("Food1");
        category.setSlug("category");
        category.setImage("www.food.vn");
        
        // when
        Long savedId = categoryService.saveCategory(category);
        Category findCategory = categoryService.getCategoryById(savedId);
        
        // then
        Assertions.assertThat(findCategory).isEqualTo(category);
    }
    
    @Test
    void DuplicateMemberException() {
        //given
        Category category1 = new Category();
        category1.setCategoryName("Food2");
        category1.setSlug("category");
        category1.setImage("www.food.vn");
        
        Category category2 = new Category();
        category2.setCategoryName("Food2");
        category2.setSlug("category");
        category2.setImage("www.food.vn");
        
        //when
        categoryService.saveCategory(category1);
        
        //then
        assertThrows(IllegalStateException.class,
                     () -> categoryService.saveCategory(category2));
    }
    
    @Test void getAllCategories() {
        // given
        Category category = new Category();
        category.setCategoryName("Food2");
        category.setSlug("category");
        category.setImage("www.food.vn");
        
        Food food1 = new Food("food1", "food1", "food desc", 3000, "www.image", 3);
        Food food2 = new Food("food2", "food2", "food desc", 3000, "www.image", 3);
        Food food3 = new Food("food3", "food3", "food desc", 3000, "www.image", 3);
        
        category.add(food1);
        category.add(food2);
        category.add(food3);
        
        List<Food> foods = new ArrayList<>();
        foods.add(food1);
        foods.add(food2);
        foods.add(food3);
        
        // when
        Long savedId = categoryService.saveCategory(category);
        Category findCategory = categoryService.getCategoryById(savedId);
        List<Food> foodList = findCategory.getListFood();
        
        // then
        Assertions.assertThat(foodList).isEqualTo(foods);
    }
}