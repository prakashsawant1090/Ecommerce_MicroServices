package com.prakash.ecommerce.inventory_service.Dto;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}
