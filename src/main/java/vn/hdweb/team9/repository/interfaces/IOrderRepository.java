package vn.hdweb.team9.repository.interfaces;

import org.aspectj.weaver.ast.Or;
import vn.hdweb.team9.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository{

    // save order
    Order save(Order order);

    // Retrieval order by order id
    Optional<Order> findById(Long orderId);


    // Retrieval order by status
    Optional<Order> findByStatus (String status);

    // Retrieval All
    List<Order> findAll();



}
