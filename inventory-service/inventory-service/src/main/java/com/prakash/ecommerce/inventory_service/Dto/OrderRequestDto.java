package com.prakash.ecommerce.inventory_service.Dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderRequestItemDto> items;
}
