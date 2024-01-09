package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.exception.FoodException;
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
        validateDuplicateFood(food, false);
        
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
        List<Food> foods = foodDAO.findByName(food.getSlug());
        return foods.isEmpty();
    }
    
    private void validateDuplicateFood(Food food, boolean isUpdate) {
        List<Food> foods;
        if (isUpdate) {
            foods = foodDAO.findByNameExceptId(food.getFoodName(),food.getId());
        } else {
            foods = foodDAO.findByName(food.getFoodName());
        }
        if (!foods.isEmpty()) {
            throw new FoodException(404, "Food already exist!");
        }
    }
    
    // get all foods
    public List<Food> getAllFoods() {
        return foodDAO.findAll();
    }
    
    // get food by id
    public Food getFoodById(Long foodId) {
        Optional<Food> theResult = Optional.ofNullable(foodDAO.finById(foodId));
        
        Food food = null;
        
        if (theResult.isPresent()) {
            food = theResult.get();
        } else {
            // we didn't find the food
            throw new RuntimeException("Did not find food id - " + foodId);
        }
        
        return food;
    }
    
    // update food
    @Transactional
    public Long updateFood(Food food) {
        validateDuplicateFood(food, true);
        
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
    
    // delete food
    @Transactional
    public Integer deleteFoodById(Integer foodId) {
        foodDAO.deleteById(foodId);
        return foodId;
    }
    
    //=== Functional Section  ===/
    // get food by name
    public List<Food> getFoodByName(Food food) {
        return foodDAO.findByName(food.getFoodName());
    }

    // get food by slug
    public Optional<Food> findBySlug(String slug) {
        return foodDAO.findBySlug(slug);
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
