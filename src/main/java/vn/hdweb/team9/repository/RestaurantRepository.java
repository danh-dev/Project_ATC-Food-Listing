package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;

import java.util.List;
import java.util.Optional;


public class RestaurantRepository implements IRestaurantRepository {
    private final EntityManager entityManager;

    public RestaurantRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        entityManager.persist(restaurant);
        return restaurant;
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        Restaurant restaurant = entityManager.find(Restaurant.class, id);
        return Optional.ofNullable(restaurant);
    }

    @Override
    public Optional<Restaurant> findByName(String restaurantName) {
        List<Restaurant> result = entityManager
                .createQuery("SELECT r " +
                                    "FROM Restaurant r " +
                                    "WHERE r.restaurant_name = :res_name", Restaurant.class)
                .setParameter("res_name", restaurantName)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Restaurant> findAll() {
        return entityManager
                .createQuery("SELECT r " +
                        "FROM Restaurant r", Restaurant.class)
                .getResultList();
    }
}
