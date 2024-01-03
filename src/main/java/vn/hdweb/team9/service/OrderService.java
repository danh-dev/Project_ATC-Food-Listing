package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.dto.order.*;
import vn.hdweb.team9.domain.entity.*;
import vn.hdweb.team9.repository.FoodRepository;
import vn.hdweb.team9.repository.OrderRepository;
import vn.hdweb.team9.repository.UserRepository;
import vn.hdweb.team9.repository.RestaurantRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    // Retrieve all DTO response orders
    @Transactional
    public List<OrderResponseDto> orderList() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            Optional<User> user = userRepository.findById(order.getUser().getId());
            OrderResponseDto orderResponse = new OrderResponseDto();
            orderResponse.setOrderId(order.getId());
            orderResponse.setUserName(user.orElse(new User()).getUsername());
            orderResponse.setUserId(order.getUser().getId());
            orderResponse.setOrderNote(order.getOrderNote());
            orderResponse.setStatus(order.getStatus().name());
            orderResponse.setCreatedAt(order.getCreatedAt());
            orderResponse.setRestaurantId(order.getRestaurant().getId());

            List<OrderDetail> orderDetails = order.getOrderItems();
            // Use method stream() and map() Java Stream API converts List<OrderDetail> into a stream of OrderDetail objects
            List<OrderDetailResponseDto> orderDetailResponses = orderDetails.stream()
                    .map(this::mapToOrderDetailResponseDto)
                    .collect(Collectors.toList());
            orderResponse.setOrderDetails(orderDetailResponses);

            return orderResponse;
        }).collect(Collectors.toList());
    }

    // Retrieval DTO response order by Id
    @Transactional
    public Optional<OrderResponseDto> orderOneResponse(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        return optionalOrder.map(order -> {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setUserId(order.getUser().getId());
            orderResponseDto.setUserName(order.getUser().getUsername());
            orderResponseDto.setRestaurantId(order.getRestaurant().getId());
            orderResponseDto.setRestaurantName(order.getRestaurant().getRestaurantName());
            orderResponseDto.setOrderNote(order.getOrderNote());
            orderResponseDto.setRatingRestaurant(order.isRatingRestaurant());
            orderResponseDto.setTotalBill(order.getTotalBill());
            orderResponseDto.setStatus(order.getStatus().name());
            orderResponseDto.setCreatedAt(order.getCreatedAt());

            List<OrderDetailResponseDto> orderDetailResponses = order.getOrderItems()
                    .stream()
                    .map(this::mapToOrderDetailResponseDto)
                    .collect(Collectors.toList());

            orderResponseDto.setOrderDetails(orderDetailResponses);

            return orderResponseDto;
        });
    }
    // Method additional fields in OrderDetailResponseDto
    private OrderDetailResponseDto mapToOrderDetailResponseDto(OrderDetail orderDetail) {
        OrderDetailResponseDto responseDto = new OrderDetailResponseDto();
        responseDto.setOrderDetailId(orderDetail.getId());
        responseDto.setFoodId(orderDetail.getFood().getId());
        responseDto.setFoodName(orderDetail.getFood().getFoodName());
        responseDto.setQuantity(orderDetail.getQuantity());
        responseDto.setRatingFood(orderDetail.isRatingFood());
        responseDto.setTotalPrice(orderDetail.getTotalPrice());
        return responseDto;
    }

    //== Association Assist Method ==//
    // User Entity
    @Transactional
    public void setUser(Long orderId, Long userId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
            order.setUser(user);
            user.getListOrder().add(order);
            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error setting user for order", e);
        }
    }
    // Restaurant Entity
    @Transactional
    public void setRestaurant(Long orderId, Long restaurantId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + restaurantId));
            order.setRestaurant(restaurant);
            restaurant.getListOrder().add(order);
            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error setting restaurant for order", e);
        }
    }
    @Transactional
    public void cancelOrder(Long orderId) {
        // LOGIC REMOVE ORDER HAS BEEN CANCELLED
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

            if (!order.getStatus().equals(OrderStatus.DELIVERED) && !order.getStatus().equals(OrderStatus.SHIPPING)){
                order.setStatus(OrderStatus.CANCEL);
                orderRepository.save(order);

                // Remove the canceled order from the user's list of orders
                User user = order.getUser();
                user.getListOrder().remove(order);
                userRepository.save(user);

            } else {
                throw new RuntimeException("Cannot cancel order in DELIVERED, SHIPPING");
            }

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error canceling order", e);
        }
    }

    public int calculateTotalPrice(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
            return order.getTotalBill();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating total price", e);
        }
    }
    @Transactional
    public void addOrderDetail(Long orderId, OrderDetailRequestDto orderDetailRequestDto) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

            // Lấy thông tin từ OrderDetailRequestDto
            Long foodId = orderDetailRequestDto.getFoodId();
            int quantity = orderDetailRequestDto.getQuantity();
            boolean isRatingFood = orderDetailRequestDto.isRatingFood();

            // Kiểm tra xem foodId có tồn tại không
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new IllegalArgumentException("Food not found with id: " + foodId));

            // Tạo mới đối tượng OrderDetail từ thông tin trong OrderDetailRequestDto
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setFood(food);
            orderDetail.setQuantity(quantity);
            orderDetail.setRatingFood(isRatingFood);
            orderDetail.setTotalPrice(food.getPrice() * quantity);

            // Thêm OrderDetail vào danh sách của Order
            order.getOrderItems().add(orderDetail);

            // Cập nhật tổng giá trị đơn hàng
            order.setTotalBill(calculateTotalPrice(orderId));

            // Lưu thay đổi vào cơ sở dữ liệu
            orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error adding order detail", e);
        }
    }
    @Transactional
    public Long createOrder(OrderRequestDto orderRequestDto) {
        try {
            // Lấy thông tin từ OrderRequestDto
            Long userId = orderRequestDto.getUserId();
            Long restaurantId = orderRequestDto.getRestaurantId();
            String orderNote = orderRequestDto.getOrderNote();
            boolean isRatingRestaurant = orderRequestDto.isRatingRestaurant();

            // Kiểm tra xem userId và restaurantId có tồn tại không
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + restaurantId));

            // Tạo mới đối tượng Order từ thông tin trong OrderRequestDto
            Order order = new Order();
            order.setUser(user);
            order.setRestaurant(restaurant);
            order.setCreatedAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PROCESSING);
            order.setOrderNote(orderNote);
            order.setRatingRestaurant(isRatingRestaurant);

            // Lưu Order vào cơ sở dữ liệu
            orderRepository.save(order);

            // Lấy danh sách chi tiết đơn hàng từ OrderRequestDto
            List<OrderDetailRequestDto> orderDetailRequestDtos = orderRequestDto.getOrderDetails();

            // Thêm chi tiết đơn hàng cho mỗi đối tượng OrderDetailRequestDto
            for (OrderDetailRequestDto orderDetailRequestDto : orderDetailRequestDtos) {
                addOrderDetail(order.getId(), orderDetailRequestDto);
            }

            // Trả về ID của Order mới tạo
            return order.getId();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error creating order", e);
        }
    }

    @Transactional
    public Long placeOrder(OrderRequestDto orderRequestDto) {
        try {
            // Tạo Order và chi tiết đơn hàng từ OrderRequestDto
            Long orderId = createOrder(orderRequestDto);

            // Set User và Restaurant cho Order
            setUser(orderId, orderRequestDto.getUserId());
            setRestaurant(orderId, orderRequestDto.getRestaurantId());

            return orderId;
        } catch (Exception e) {
            throw new RuntimeException("Error placing order", e);
        }
    }
    @Transactional
    public void updateOrderStatus(Long orderId, OrderUpdateRequestDto orderUpdateDto) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
            // Update only the status
            order.setStatus(orderUpdateDto.getNewStatus());
            // Save the updated order
            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error updating order status", e);
        }
    }
}
