package com.prakash.ecommerce.inventory_service.Repository;

import com.prakash.ecommerce.inventory_service.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
