package vn.hdweb.team9.repository.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.RestaurantRepository;

@SpringBootTest
@Transactional
class RestaurantRepositoryTest {
    private RestaurantRepository restaurantRepository;

    @Test
    public void findExactByName() {
        // given
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantName("Phở Mỹ");
        

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantName("Phở My");

        // when

        // then
    }

}