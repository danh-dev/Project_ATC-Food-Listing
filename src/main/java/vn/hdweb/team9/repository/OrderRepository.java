package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.repository.interfaces.IOrderRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements IOrderRepository {

    private final EntityManager entityManager;

    @Override
    public Order save(Order order) {
        try {
            entityManager.persist(order);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving order", e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try {
            Order order = entityManager.find(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding order by ID", e);
        }
    }

    @Override
    public Optional<Order> findByStatus(String status) {
        try {
            List<Order> result = entityManager.createQuery("SELECT o FROM Order o WHERE o.status = :status", Order.class)
                    .setParameter("status", status)
                    .getResultList();
            return result.stream().findAny();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding order by status", e);
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            return entityManager
                    .createQuery("SELECT o FROM Order o", Order.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding all orders", e);
        }
    }
}
