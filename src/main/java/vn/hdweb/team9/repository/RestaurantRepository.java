package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RestaurantRepository implements IRestaurantRepository {
    @Autowired
    private EntityManager em;

    @Override
    public void save(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            em.persist(restaurant);
        } else {
            em.merge(restaurant);
        }
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return Optional.ofNullable(em.find(Restaurant.class, id));
    }


    @Override
    public List<Restaurant> findAll() {
        return em.createQuery("select * r from RESTAURANT r").getResultList();
    }

    @Override
    public List<Restaurant> findByRestaurantName(String restaurantName) {
        return em.createQuery("select * r from RESTAURANT r where r.restaurant_name = :name", Restaurant.class).
                setParameter("name", restaurantName).
                getResultList();
    }

    @Override
    public Restaurant findBySlug(String slug) {
        return null;
    }
}
