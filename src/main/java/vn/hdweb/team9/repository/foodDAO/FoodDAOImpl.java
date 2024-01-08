package vn.hdweb.team9.repository.foodDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FoodDAOImpl implements FoodDAO {
    
    private final EntityManager entityManager;
    
    /**
     * Food
     * */
    @Override
    public void save(Food food) {
        entityManager.persist(food);
    }
    
    @Override
    public List<Food> findAll() {
        return entityManager
                .createQuery("select f from Food f", Food.class)
                .getResultList();
    }
    
    @Override
    public Food finById(Long foodId) {
        return entityManager.find(Food.class, foodId);
    }
    
    @Override
    public List<Food> findByName(String foodName) {
        TypedQuery<Food> query = entityManager
                .createQuery("from Food f where f.foodName = :data", Food.class)
                .setParameter("data", foodName);
        
        return query.getResultList();
    }
    
    @Override
    public List<Food> findBySlug(String foodSlug) {
        TypedQuery<Food> query = entityManager
                .createQuery("from Food f where f.slug = :data", Food.class)
                .setParameter("data", foodSlug);
        
        return query.getResultList();
    }
    
    /**
     * Rating
     * */
    @Override
    public List<RatingFood> findRatingsByFoodId(Long foodId) {
        TypedQuery<RatingFood> query = entityManager
                .createQuery("from RatingFood r where r.food.id = :data", RatingFood.class)
                .setParameter("data", foodId);
        
        return query.getResultList();
    }
    
    @Override
    public Food findFoodByIdJoinFetch(Long foodId) {
        TypedQuery<Food> query = entityManager
                .createQuery("from Food f " +
                             "join fetch f.listRatingFood " +
                             "join fetch f.id " +
                             "where f.id = :data", Food.class)
                .setParameter("data", foodId);
        
        return query.getSingleResult();
    }
    
    @Override
    public void deleteById(Integer foodId) {
        Food food = entityManager.find(Food.class, foodId);
        
        List<RatingFood> ratingList = food.getListRatingFood();
        
        // break associate
        for (RatingFood ratingFood: ratingList) {
            ratingFood.setFood(null);
        }
        
        entityManager.remove(food);
    }
    
    @Override public void update(Food food) {
        entityManager.merge(food);
    }
}
