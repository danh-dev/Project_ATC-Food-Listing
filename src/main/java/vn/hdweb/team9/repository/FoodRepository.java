package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IFoodRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FoodRepository implements IFoodRepository {
    private final EntityManager entityManager;

    public FoodRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Food> findAll() {
        return entityManager
                .createQuery("select f from Food f", Food.class)
                .getResultList();
    }
    @Override
    public Optional<Food> findById(Long foodId) {
        Food food = entityManager.find(Food.class, foodId);
        return Optional.ofNullable(food);
    }


}
