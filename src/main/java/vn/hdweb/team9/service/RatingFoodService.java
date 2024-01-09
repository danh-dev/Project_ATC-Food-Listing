package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.dto.respon.RatingFoodDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.FoodRepository;
import vn.hdweb.team9.repository.interfaces.RatingFoodRepository;
import vn.hdweb.team9.service.imp.IUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class RatingFoodService {
    private final RatingFoodRepository ratingFoodRepository;
    private final FoodRepository foodRepository;
    private final IUserService userService;

    /*
     * add new
     */
    @Transactional
    public Long rateFood(Long foodId, String content, int rateStar) {
        // Retrieve Entities
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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
     * findALL
     */
    public List<RatingFoodDto> findAll() {
        List<RatingFood> ratingFoodList = ratingFoodRepository.findAll();

        List<RatingFoodDto> ratingResponseList = getRatingResponseList(ratingFoodList);
        return ratingResponseList;
    }


    /*
     * get by food Id
     */
    public List<RatingFoodDto> getRatingsByFoodId(Long foodId) {
        List<RatingFood> ratingFoodList = ratingFoodRepository.findByFoodId(foodId);

        List<RatingFoodDto> ratingResponseList = getRatingResponseList(ratingFoodList);

        return ratingResponseList;
    }

    /*
     * transform rating response DTO
     */

    private List<RatingFoodDto> getRatingResponseList(List<RatingFood> ratingFoodList) {
        List<RatingFoodDto> ratingResponseList = new ArrayList<>();
        for (RatingFood ratingFood : ratingFoodList) {
            RatingFoodDto ratingFoodDto = new RatingFoodDto();

            ratingFoodDto.setId(ratingFood.getId());
            ratingFoodDto.setContent(ratingFood.getContent());
            ratingFoodDto.setRateStar(ratingFood.getRateStar());
            ratingFoodDto.setCreatedAt(ratingFood.getCreatedAt());
            ratingFoodDto.setFoodName(ratingFood.getFood().getFoodName());
            ratingFoodDto.setUserFullName(ratingFood.getUser().getFullName());
            ratingFoodDto.setUserAvatar(ratingFood.getUser().getAvatar());
            ratingResponseList.add(ratingFoodDto);
        }
        return ratingResponseList;
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
     * count rating by Food id
     */
    public long countRating(Long foodId) {
        return ratingFoodRepository.countByFoodId(foodId);
    }

    /*
     * delete
     */
    public void deleteRating(Long ratingId) {
        ratingFoodRepository.deleteById(ratingId);
    }
}
