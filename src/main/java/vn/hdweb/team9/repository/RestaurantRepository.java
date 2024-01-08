package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;

import java.util.List;

@Transactional
public class RestaurantRepository implements IRestaurantRepository {
    private EntityManager em;

    @Override
    public void save(Restaurant restaurant) {

        em.persist(restaurant);
    }

    @Override
    public List<Restaurant> findByRestaurantName(String restaurantName) {
        return em.createQuery("select * r from RESTAURANT r where r.restaurant_name = :name", Restaurant.class).
                setParameter("name", restaurantName).
                getResultList();
    }

    @Override
    public List<Restaurant> findExactByRestaurantName(String restaurantName) {
        String jpql = "SELECT r FROM Restaurant r WHERE r.name = :name COLLATE utf8_bin";
        TypedQuery<Restaurant> query = em.createQuery(jpql, Restaurant.class);
        query.setParameter("name", restaurantName);
        return query.getResultList();
    }

    @Override
    public Restaurant findBySlug(String slug) {
        return null;
    }
}
