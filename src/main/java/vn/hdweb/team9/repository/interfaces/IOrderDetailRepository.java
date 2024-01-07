package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.hdweb.team9.domain.entity.OrderDetail;
import vn.hdweb.team9.domain.entity.Role;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findOrderDetailById(Long id);
}
