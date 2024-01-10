package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.Restaurant;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {


//    void save(Restaurant restaurant);
    //Optional<Restaurant> findById(Long id);
    List<Restaurant> findByRestaurantName(String restaurantName);

    List<Restaurant> findExactByRestaurantName(String restaurantName);

    Restaurant findBySlug(String slug);

    //List<Restaurant> findAll();
    
    // find all restaurants by food name
//    @Query("SELECT r FROM Restaurant r LEFT JOIN Food f ON r.id = f.restaurant.id WHERE (f.foodName LIKE %:foodName% OR r.restaurantName LIKE %:foodName%)")
    @Query("SELECT r FROM Restaurant r LEFT JOIN r.listFood f WHERE f.foodName LIKE %:foodName% OR r.restaurantName LIKE %:foodName%")
    public Set<Restaurant> FindAllWithFoodNameQuery(@Param("foodName") String foodName);
}
