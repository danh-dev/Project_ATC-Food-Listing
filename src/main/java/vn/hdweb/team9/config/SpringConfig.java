package vn.hdweb.team9.config;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.hdweb.team9.repository.RestaurantRepository;
import vn.hdweb.team9.service.RestaurantService;

@Configuration
public class SpringConfig {
    private final EntityManager entityManager;

    public SpringConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public RestaurantService restaurantService() {
        return new RestaurantService(restaurantRepository());
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return new RestaurantRepository(entityManager);
    }
}
