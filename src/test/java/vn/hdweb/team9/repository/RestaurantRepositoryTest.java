package vn.hdweb.team9.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.RestaurantRepository;
import vn.hdweb.team9.service.RestaurantService;

import java.util.List;

@SpringBootTest
@Transactional
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    RestaurantService restaurantService;

    @Test
    public void findExactByName() {
        // given
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantName("Phở Mỹ");
        restaurantRepository.save(restaurant1);
        

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantName("Phở My");
        restaurantRepository.save(restaurant2);

        // when
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantName(restaurant1.getRestaurantName());

        // then
        Assertions.assertThat(restaurants.get(0).getRestaurantName()).isNotEqualTo(restaurant1.getRestaurantName());
    }

}