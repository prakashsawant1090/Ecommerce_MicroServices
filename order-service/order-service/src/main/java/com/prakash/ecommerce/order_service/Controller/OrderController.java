package com.prakash.ecommerce.order_service.Controller;

import com.prakash.ecommerce.order_service.Dto.OrderRequestDto;
import com.prakash.ecommerce.order_service.Entity.Order;
import com.prakash.ecommerce.order_service.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;



    @GetMapping("/hellorders")
    public String fetchOrder(){
        return "Hello order from [ Order-Service ].";
    }

    @GetMapping("/test")
    public ResponseEntity<String> tsetController() {
        return ResponseEntity.ok("Order Controller working");
    }


    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest httpServletRequest) {
        log.info("Fetching all orders via controller");
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);  // Returns 200 OK with the list of orders
    }

    @PostMapping("/create")
    public ResponseEntity<OrderRequestDto> createorder(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(orderService.createorder(orderRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with ID: {} via controller", id);
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);  // Returns 200 OK with the order
    }


}
