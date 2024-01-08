package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.exception.CategoryException;
import vn.hdweb.team9.repository.foodDAO.FoodDAO;
import vn.hdweb.team9.utility.StringToSlugUtil;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService {
    private final FoodDAO foodDAO;
    
    // insert food
    @Transactional
    public Long saveFood(Food food) {
        // validate duplicate food name
        validateDuplicateFood(food);
        
        // trim food name white spaces
        String result = food.getFoodName()
                                .replaceAll("[^\\p{L}\\s]", "")
                                .trim();
        food.setFoodName(result);
        
        // convert string to slug
        String resultSlug = StringToSlugUtil.toSlug(food.getFoodName());
        
        
        boolean isDuplicateSlug = validateDuplicateFoodSlug(food);
       
        if (isDuplicateSlug) {
            food.setSlug(resultSlug);
        } else {
            String newSlug = resultSlug + "-" + food.getId();
            food.setSlug(newSlug);
        }
        
        foodDAO.save(food);
        
        return food.getId();
    }
    
    private boolean validateDuplicateFoodSlug(Food food) {
        List<Food> categories = foodDAO.findByName(food.getSlug());
        return categories.isEmpty();
    }
    
    private void validateDuplicateFood(Food food) {
        List<Food> categories = foodDAO.findByName(food.getFoodName());
        if (!categories.isEmpty()) {
            throw new CategoryException(404, "Category already exist!");
        }
    }
    
    // get all categories
    public List<Food> getAllCategories() {
        return foodDAO.findAll();
    }
    
    // get category by id
    public Food getCategoryById(Long categoryId) {
        Optional<Food> theResult = Optional.ofNullable(foodDAO.finById(categoryId));
        
        Food food = null;
        
        if (theResult.isPresent()) {
            food = theResult.get();
        } else {
            // we didn't find the category
            throw new RuntimeException("Did not find category id - " + categoryId);
        }
        
        return food;
    }
    
    // update category
    @Transactional
    public Long updateCategory(Food food) {
        // convert string to slug
        String resultSlug = StringToSlugUtil.toSlug(food.getFoodName());
        
        boolean isDuplicateSlug = validateDuplicateFoodSlug(food);
        
        if (isDuplicateSlug) {
            food.setSlug(resultSlug);
        } else {
            String newSlug = resultSlug + "-" + food.getId();
            food.setSlug(newSlug);
        }
        
        foodDAO.update(food);
        return food.getId();
    }
    
    // delete category
    @Transactional
    public Integer deleteCategoryById(Integer categoryId) {
        foodDAO.deleteById(categoryId);
        return categoryId;
    }
    
    //=== Functional Section  ===/
    // get food by name
    public List<Food> getFoodByName(Food food) {
        return foodDAO.findByName(food.getFoodName());
    }
    
    // get food by slug
    public List<Food> getCategoryBySlug(Food food) {
        return foodDAO.findBySlug(food.getSlug());
    }
    
    // get ratings by food id
    public List<RatingFood> getRatingsByFoodId(Food food) {
        return foodDAO.findRatingsByFoodId(food.getId());
    }
    
    // get all ratings and food at times
    public Food getFoodByIdJoinFetch(Food food) {
        return foodDAO.findFoodByIdJoinFetch(food.getId());
    }
}
