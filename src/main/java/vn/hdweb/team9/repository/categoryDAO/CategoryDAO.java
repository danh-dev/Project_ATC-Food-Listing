package vn.hdweb.team9.repository.categoryDAO;

import vn.hdweb.team9.domain.entity.Category;
import vn.hdweb.team9.domain.entity.Food;

import java.util.List;

public interface CategoryDAO {
    // save category and return saved Id
    void save(Category category);
    
    // find all category
    List<Category> findAll();
    
    // find category by id
    Category finById(Long categoryId);
    
    // find category by name
    List<Category> findByName(String categoryName);
    
    // find category by slug
    List<Category> findBySlug(String categorySlug);
    
    // find category except current id
    List<Category> findByNameExceptId(String categoryName, Long currentCategoryId);
    
    // find foods by category id
    List<Food> findFoodsByCategoryId(Long categoryId);
    
    // get category and foods at times
    Category findCategoryByIdJoinFetch(Long categoryId);
    
    // delete category by id
    void deleteById(Integer categoryId);
    
    // update category
    void update(Category category);
}
