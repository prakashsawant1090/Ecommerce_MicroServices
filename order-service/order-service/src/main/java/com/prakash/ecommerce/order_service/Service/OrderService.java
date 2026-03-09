package com.prakash.ecommerce.order_service.Service;

import com.prakash.ecommerce.order_service.Dto.OrderRequestDto;
import com.prakash.ecommerce.order_service.Entity.Order;
import com.prakash.ecommerce.order_service.Entity.OrderItem;
import com.prakash.ecommerce.order_service.Entity.enums.OrderStatus;
import com.prakash.ecommerce.order_service.FeignClients.InventoryFeignClient;
import com.prakash.ecommerce.order_service.Repository.OrderRepo;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepo orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDto.class);
    }

    @Transactional
    public OrderRequestDto createorder(OrderRequestDto orderRequestDto) {
        Double price = inventoryFeignClient.reduceStock(orderRequestDto);

        Order order = modelMapper.map(orderRequestDto,Order.class);

        for(OrderItem orderItem : order.getItems()){
            orderItem.setOrder(order);
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalPrice(price);

        Order savedorder = orderRepository.save(order);

        return modelMapper.map(savedorder,OrderRequestDto.class);

    }
}
