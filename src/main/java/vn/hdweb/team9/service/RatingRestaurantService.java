package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.dto.respon.RatingFoodDto;
import vn.hdweb.team9.domain.dto.respon.RatingRestaurantDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IUserRepository;
import vn.hdweb.team9.repository.interfaces.RatingRestaurantRepository;
import vn.hdweb.team9.repository.interfaces.RestaurantRepository;
import vn.hdweb.team9.service.imp.IUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingRestaurantService {
    private final RatingRestaurantRepository ratingRestaurantRepository;
    private final RestaurantRepository restaurantRepository;
    private final IUserService userService;

    /*
     * add new
     */
    @Transactional
    public Long rateRestaurant(Long restaurantId, String content, int rateStar) {
        // Retrieve Entities
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Create Rating
        RatingRestaurant rating = new RatingRestaurant();
        rating.setContent(content);
        rating.setRateStar(rateStar);
        rating.setCreatedAt(LocalDateTime.now());
        rating.setUser(user);
        rating.setRestaurant(restaurant);

        // Save Rating
        ratingRestaurantRepository.save(rating);

        return rating.getId();
    }

    /*
     * findAll
     */
    public List<RatingRestaurantDto> findAll() {
        List<RatingRestaurant> ratingRestaurantList = ratingRestaurantRepository.findAll();
        List<RatingRestaurantDto> ratingResponseList = getRatingResponseList(ratingRestaurantList);
        return ratingResponseList;
    }


    /*
     * get by restaurant Id
     */
    public List<RatingRestaurantDto> getRatingsByRestaurantId(Long restaurantId) {
        List<RatingRestaurant> ratingRestaurantList = ratingRestaurantRepository.findByRestaurantId(restaurantId);
        List<RatingRestaurantDto> ratingResponseList = getRatingResponseList(ratingRestaurantList);
        return ratingResponseList;
    }

    /*
     * transform rating response DTO
     */
    private List<RatingRestaurantDto> getRatingResponseList(List<RatingRestaurant> ratingRestaurantList) {
        List<RatingRestaurantDto> ratingResponseList = new ArrayList<>();

        //    transform data DTO
        for (RatingRestaurant ratingRestaurant : ratingRestaurantList) {
            RatingRestaurantDto ratingRestaurantDto = new RatingRestaurantDto();

            ratingRestaurantDto.setId(ratingRestaurant.getId());
            ratingRestaurantDto.setContent(ratingRestaurant.getContent());
            ratingRestaurantDto.setRateStar(ratingRestaurant.getRateStar());
            ratingRestaurantDto.setCreatedAt(ratingRestaurant.getCreatedAt());
            ratingRestaurantDto.setRestaurantName(ratingRestaurant.getRestaurant().getRestaurantName());
            ratingRestaurantDto.setUserFullName(ratingRestaurant.getUser().getFullName());
            ratingRestaurantDto.setUserAvatar(ratingRestaurant.getUser().getAvatar());

            ratingResponseList.add(ratingRestaurantDto);
        }
        return ratingResponseList;
    }

    /*
     * get by restaurantId and userId
     */
    public Optional<RatingRestaurant> getRatingByRestaurantIdAndUserId(Long restaurantId, Long userId) {
        return ratingRestaurantRepository.findByRestaurantIdAndUserId(restaurantId, userId);
    }

    /*
     * calAVGRatingByRestaurantId
     */
    public Double calculateAverageRatingByRestaurantId(Long restaurantId) {
        return ratingRestaurantRepository.calAVGRatingByRestaurantId(restaurantId);
    }

    /*
     * count rating by restaurant id
     */
    public long countRating(Long restaurantId) {
        return ratingRestaurantRepository.countByRestaurantId(restaurantId);
    }

    /*
     * delete
     */
    public void deleteRating(Long ratingId) {
        ratingRestaurantRepository.deleteById(ratingId);
    }
}
