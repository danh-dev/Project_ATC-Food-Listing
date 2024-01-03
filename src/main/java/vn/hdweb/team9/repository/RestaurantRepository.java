package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository implements IRestaurantRepository {
    private final EntityManager entityManager;


    @Override
    public List<Restaurant> findAll() {
        return entityManager
                .createQuery("select r from Restaurant r", Restaurant.class)
                .getResultList();
    }


    @Override
    public Optional<Restaurant> findById(Long id) {
        Restaurant restaurant = entityManager.find(Restaurant.class, id);
        return Optional.ofNullable(restaurant);
    }

    @Override
    public Optional<Restaurant> findByFoodId (Long foodId) {
            Restaurant restaurant = entityManager.createQuery("SELECT r FROM Restaurant r JOIN r.listFood f WHERE f.id = :foodId", Restaurant.class)
                    .setParameter("foodId", foodId)
                    .getSingleResult();
            return Optional.ofNullable(restaurant);

    }

}
