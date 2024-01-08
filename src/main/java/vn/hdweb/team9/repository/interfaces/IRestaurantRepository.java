package vn.hdweb.team9.repository.interfaces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.dto.respon.RestaurantDto;
import vn.hdweb.team9.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IRestaurantRepository{

    void save(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findAll();

    List<Restaurant> findByRestaurantName(String restaurantName);

    Restaurant findBySlug(String slug);

    //List<Restaurant> findAll();
}
