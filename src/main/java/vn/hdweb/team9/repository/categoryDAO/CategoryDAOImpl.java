package vn.hdweb.team9.repository.categoryDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Category;
import vn.hdweb.team9.domain.entity.Food;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryDAOImpl implements CategoryDAO{
    
    private final EntityManager entityManager;
    
    @Override
    public void save(Category category) {
        entityManager.persist(category);
    }
    
    @Override public List<Category> findAll() {
        return entityManager
                .createQuery("select c from Category c", Category.class)
                .getResultList();
    }
    
    @Override
    public Category finById(Long categoryId) {
        return entityManager.find(Category.class, categoryId);
    }
    
    @Override
    public List<Category> findByName(String categoryName) {
        TypedQuery<Category> query = entityManager
                .createQuery("from Category c where c.categoryName = :data", Category.class)
                .setParameter("data", categoryName);
        
        return query.getResultList();
    }
    
    @Override
    public List<Category> findBySlug(String categorySlug) {
        TypedQuery<Category> query = entityManager
                .createQuery("from Category c where c.slug = :data", Category.class)
                .setParameter("data", categorySlug);
        
        return query.getResultList();
    }
    
    @Override
    public List<Food> findFoodsByCategoryId(Long categoryId) {
        TypedQuery<Food> query = entityManager
                .createQuery("from Food f where f.category.id = :data", Food.class)
                .setParameter("data", categoryId);
        
        return query.getResultList();
    }
    
    @Override
    public Category findCategoryByIdJoinFetch(Long categoryId) {
        TypedQuery<Category> query = entityManager
                .createQuery("from Category c " +
                             "join fetch c.listFood " +
                             "join fetch c.id " +
                             "where c.id = :data", Category.class)
                .setParameter("data", categoryId);
        
        return query.getSingleResult();
    }
    
    @Override
    public void deleteById(Integer categoryId) {
        Category category = entityManager.find(Category.class, categoryId);
        
        List<Food> foodList = category.getListFood();
        
        // break associate
        for (Food food: foodList) {
            food.setCategory(null);
        }
        
        entityManager.remove(category);
    }
    
    @Override
    public void update(Category category) {
        entityManager.merge(category);
    }
}
