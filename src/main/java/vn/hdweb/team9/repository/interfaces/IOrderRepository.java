package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.User;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    Order findOrderById(Long id);

    List<Order> findAllByUser(User user);
}
