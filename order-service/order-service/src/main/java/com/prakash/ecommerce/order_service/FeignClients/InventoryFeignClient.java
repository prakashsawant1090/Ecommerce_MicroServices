package com.prakash.ecommerce.order_service.FeignClients;

import com.prakash.ecommerce.order_service.Dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service",path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping("/products/reduce-stock")
    Double reduceStock(@RequestBody OrderRequestDto orderRequestDto);
}
