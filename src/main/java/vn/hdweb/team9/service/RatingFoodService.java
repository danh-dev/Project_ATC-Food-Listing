package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.FoodRepository;
import vn.hdweb.team9.repository.interfaces.RatingFoodRepository;
import vn.hdweb.team9.repository.interfaces.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class RatingFoodService {
    private final RatingFoodRepository ratingFoodRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    /*
     * add new
     */
    @Transactional
    public Long rateFood(Long userId, Long foodId, String content, int rateStar) {
        // Retrieve Entities
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found"));

        // Create Rating
        RatingFood rating = new RatingFood();
        rating.setContent(content);
        rating.setRateStar(rateStar);
        rating.setCreatedAt(LocalDateTime.now());
        rating.setUser(user);
        rating.setFood(food);

        // Save Rating
        ratingFoodRepository.save(rating);

        return rating.getId();
    }

    /*
     * get by food Id
     */
    public List<RatingFood> getRatingsByFoodId(Long foodId) {
        return ratingFoodRepository.findByFoodId(foodId);
    }

    /*
     * Get rating by foodId and userId
     */
    public Optional<RatingFood> getRatingByFoodIdAndUserId(Long foodId, Long userId) {
        return ratingFoodRepository.findByFoodIdAndUserId(foodId, userId);
    }

    /*
     * calculateAverageRatingByFoodId
     */
    public Double calculateAverageRatingByFoodId(Long foodId) {
        return ratingFoodRepository.calculateAverageRatingByFoodId(foodId);
    }

    /*
     * delete
     */
    public void deleteRating(Long ratingId) {
        ratingFoodRepository.deleteById(ratingId);
    }
}
