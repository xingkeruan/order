package com.edu.order.repository;

import com.edu.order.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    @Query(value = "select * from orders where email=?1 and status='DRAFT'",nativeQuery = true)
    List<Order> getOrderByEmail(String email);
}
