package com.edu.order.repository;

import com.edu.order.domain.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Long> {
}
