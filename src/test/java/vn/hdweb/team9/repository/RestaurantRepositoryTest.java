package vn.hdweb.team9.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.RestaurantRepository;

import java.util.List;

@SpringBootTest
class RestaurantRepositoryTest {
    private RestaurantRepository res;

    @Test
    public void findExactByName() {
        // given
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantName("Phở Mỹ");
        res.save(restaurant1);
        

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantName("Phở My");
        res.save(restaurant2);

        // when
        List<Restaurant> restaurants = res.findExactByRestaurantName(restaurant1.getRestaurantName());

        // then
        Assertions.assertThat(restaurants.get(0).getRestaurantName()).isNotEqualTo(restaurant1.getRestaurantName());
    }

}