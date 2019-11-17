package com.edu.order.service;

import com.edu.order.domain.OrderDetail;

public interface IOrderDetailService {
    public int dishSetCount(long orderDetailId,int count);
    public OrderDetail addOrderDetail(OrderDetail orderDetail);

}
