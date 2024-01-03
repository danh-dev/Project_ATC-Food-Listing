package vn.hdweb.team9.repository.interfaces;


import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailRepository {

    OrderDetail save(OrderDetail orderDetail);

    Optional<OrderDetail> findById(Long id);

    // Retrieval user
    Optional<OrderDetail> findByOrder(Long orderID);

    void saveAll(List<OrderDetail> orderDetails);
    // Retrieval All

}
