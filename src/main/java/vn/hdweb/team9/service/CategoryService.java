package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Category;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.exception.CategoryException;
import vn.hdweb.team9.repository.categoryDAO.CategoryDAO;
import vn.hdweb.team9.utility.StringToSlugUtil;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDAO categoryDAO;
    
    // insert category
    @Transactional
    public Long saveCategory(Category category) {
        // validate duplicate category name
        validateDuplicateCategory(category);
        
        // trim category name white spaces
        String result = category.getCategoryName()
                                .replaceAll("[^\\p{L}\\s]", "")
                                .trim();
        category.setCategoryName(result);
        
        // convert string to slug
        String resultSlug = StringToSlugUtil.toSlug(category.getCategoryName());
        
        
        boolean isDuplicateSlug = validateDuplicateCategorySlug(category);
       
        if (isDuplicateSlug) {
            category.setSlug(resultSlug);
        } else {
            String newSlug = resultSlug + "-" + category.getId();
            category.setSlug(newSlug);
        }
        
        categoryDAO.save(category);
        
        return category.getId();
    }
    
    private boolean validateDuplicateCategorySlug(Category category) {
        List<Category> categories = categoryDAO.findByName(category.getSlug());
        return categories.isEmpty();
    }
    
    private void validateDuplicateCategory(Category category) {
        List<Category> categories = categoryDAO.findByName(category.getCategoryName());
        if (!categories.isEmpty()) {
            throw new CategoryException(404, "Category already exist!");
        }
    }
    
    // get all categories
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }
    
    // get category by id
    public Category getCategoryById(Long categoryId) {
        Optional<Category> theResult = Optional.ofNullable(categoryDAO.finById(categoryId));
        
        Category category = null;
        
        if (theResult.isPresent()) {
            category = theResult.get();
        } else {
            // we didn't find the category
            throw new RuntimeException("Did not find category id - " + categoryId);
        }
        
        return category;
    }
    
    // update category
    @Transactional
    public Long updateCategory(Category category) {
        // convert string to slug
        String resultSlug = StringToSlugUtil.toSlug(category.getCategoryName());
        
        boolean isDuplicateSlug = validateDuplicateCategorySlug(category);
        
        if (isDuplicateSlug) {
            category.setSlug(resultSlug);
        } else {
            String newSlug = resultSlug + "-" + category.getId();
            category.setSlug(newSlug);
        }
        
        categoryDAO.update(category);
        return category.getId();
    }
    
    // delete category
    @Transactional
    public Integer deleteCategoryById(Integer categoryId) {
        categoryDAO.deleteById(categoryId);
        return categoryId;
    }
    
    //=== Functional Section  ===/
    // get category by name
    public List<Category> getCategoryByName(Category category) {
        return categoryDAO.findByName(category.getCategoryName());
    }
    
    // get category by slug
    public List<Category> getCategoryBySlug(Category category) {
        return categoryDAO.findBySlug(category.getSlug());
    }
    
    // get foods by category id
    public List<Food> getFoodsByCategoryId(Category category) {
        return categoryDAO.findFoodsByCategoryId(category.getId());
    }
    
    // get all foods and category at times
    public Category getCategoryByIdJoinFetch(Category category) {
        return categoryDAO.findCategoryByIdJoinFetch(category.getId());
    }
}
