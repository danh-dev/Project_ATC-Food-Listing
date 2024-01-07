package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.repository.interfaces.IOrderDetailRepository;
import vn.hdweb.team9.repository.interfaces.IOrderRepository;
import vn.hdweb.team9.repository.interfaces.IUserRepository;
import vn.hdweb.team9.service.imp.IOrderService;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService  {

    private final IOrderRepository orderRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final IUserRepository userRepository;

    @Override
    public void createOrder() {
        // TODO implement here
    }

}
