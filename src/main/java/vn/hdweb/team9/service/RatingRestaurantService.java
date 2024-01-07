package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.RatingRestaurantRepository;
import vn.hdweb.team9.repository.interfaces.RestaurantRepository;
import vn.hdweb.team9.repository.interfaces.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingRestaurantService {
    private final RatingRestaurantRepository ratingRestaurantRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    /*
     * add new
     */
    @Transactional
    public Long rateRestaurant(Long userId, Long restaurantId, String content, int rateStar) {
        System.out.println("service------------>>>..");
        // Retrieve Entities
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        System.out.println(restaurant);
        System.out.println(restaurant.getRestaurantName());

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
     * get by restaurant Id
     */
    public List<RatingRestaurant> getRatingsByRestaurantId(Long restaurantId) {
        return ratingRestaurantRepository.findByRestaurantId(restaurantId);
    }

    /*
     * get by restaurantId and userId
     */
    public Optional<RatingRestaurant> getRatingByRestaurantIdAndUserId(Long restaurantId, Long userId){
        return ratingRestaurantRepository.findByRestaurantIdAndUserId(restaurantId,userId);
    }

    /*
     * calAVGRatingByRestaurantId
     */
    public Double calculateAverageRatingByRestaurantId(Long restaurantId) {
        return ratingRestaurantRepository.calAVGRatingByRestaurantId(restaurantId);
    }

    /*
     * delete
     */
    public void deleteRating(Long ratingId) {
        ratingRestaurantRepository.deleteById(ratingId);
    }
}
