package com.prakash.ecommerce.inventory_service.Controller;

import com.prakash.ecommerce.inventory_service.Dto.OrderRequestDto;
import com.prakash.ecommerce.inventory_service.Dto.ProductDto;
import com.prakash.ecommerce.inventory_service.FeignClients.OrdersFeignClient;
import com.prakash.ecommerce.inventory_service.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/test")
    public ResponseEntity<String> tsetController() {
        return ResponseEntity.ok("Inventory Controller working");
    }



    @GetMapping("/fetchOrder")
    public String fetchOrders() {

//        ServiceInstance orderInstance = discoveryClient.getInstances("order-service").getFirst();
//
//        return  restClient.get()
//                .uri(orderInstance.getUri()+"/orders/core/hellorders")
//                .retrieve()
//                .body(String.class);

        return ordersFeignClient.helloOrders();

    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getInventoryById(@PathVariable Long id) {
        ProductDto inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/reduce-stock")
    public Double reduceStock(@RequestBody OrderRequestDto orderRequestDto){
        return productService.reduceStock(orderRequestDto);
    }
}
