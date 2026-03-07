package com.prakash.ecommerce.order_service.Repository;

import com.prakash.ecommerce.order_service.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
