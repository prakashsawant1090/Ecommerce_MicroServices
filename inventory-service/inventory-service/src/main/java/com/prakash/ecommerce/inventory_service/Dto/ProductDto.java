package com.prakash.ecommerce.inventory_service.Dto;


import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    private String name;

    private Double price;

    private Integer stock;
}