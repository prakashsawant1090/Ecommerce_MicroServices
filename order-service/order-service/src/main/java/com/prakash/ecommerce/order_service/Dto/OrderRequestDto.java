package com.prakash.ecommerce.order_service.Dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDto {
    private Long id;
    private List<OrderRequestItemDto> items;
    private BigDecimal totalPrice;
}
