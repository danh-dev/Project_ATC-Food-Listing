package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.OrderDetail;
import vn.hdweb.team9.repository.interfaces.IOrderDetailRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDetailRepository implements IOrderDetailRepository {

    private final EntityManager entityManager;

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        try {
            entityManager.persist(orderDetail);
            return orderDetail;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving order detail", e);
        }
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        try {
            OrderDetail orderDetail = entityManager.find(OrderDetail.class, id);
            return Optional.ofNullable(orderDetail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding order detail by ID", e);
        }
    }

    @Override
    public Optional<OrderDetail> findByOrder(Long orderId) {
        try {
            List<OrderDetail> result = entityManager.createQuery(
                            "SELECT od FROM OrderDetail od WHERE od.order.id = :orderId", OrderDetail.class)
                    .setParameter("orderId", orderId)
                    .getResultList();
            return result.stream().findAny();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding order detail by order ID", e);
        }
    }

    @Override
    public void saveAll(List<OrderDetail> orderDetails) {
        try {
            for (OrderDetail orderDetail : orderDetails) {
                entityManager.persist(orderDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving list of order details", e);
        }
    }
}
