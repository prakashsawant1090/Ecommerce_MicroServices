package com.prakash.ecommerce.order_service.Service;

import com.prakash.ecommerce.order_service.Dto.OrderRequestDto;
import com.prakash.ecommerce.order_service.Entity.Order;
import com.prakash.ecommerce.order_service.Entity.OrderItem;
import com.prakash.ecommerce.order_service.Entity.enums.OrderStatus;
import com.prakash.ecommerce.order_service.FeignClients.InventoryFeignClient;
import com.prakash.ecommerce.order_service.Repository.OrderRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
    //@Retry(name = "inventoryRetry",fallbackMethod = "inventoryRetryFallBack")
   // @RateLimiter(name = "inventoryRateLimiter",fallbackMethod = "inventoryRetryFallBack")
    @CircuitBreaker(name = "inventoryCircketBreaker",fallbackMethod = "inventoryRetryFallBack")
    public OrderRequestDto createorder(OrderRequestDto orderRequestDto) {
        log.info("trying to crete the order");
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


    public OrderRequestDto inventoryRetryFallBack(OrderRequestDto orderRequestDto,Throwable throwable) {

        log.info("inventoryRetryFallBack called..");

        log.info("API  failed due to {} ",throwable.getLocalizedMessage());

        return new OrderRequestDto();

    }


}
