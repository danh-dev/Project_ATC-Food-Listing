package vn.hdweb.team9.service;

import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.repository.FoodRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }
    public List<Food> findFoodAll() {
        return foodRepository.findAll();
    }

    public Optional<Food> findFoodOne(Long foodId) {
        return foodRepository.findById(foodId);
    }
}
