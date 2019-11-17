package com.edu.order.service;

import com.edu.order.domain.Order;

public interface IOrderService {
    public Order getOrderById(long id);
    public Order createOrder();
    public Order addDishToOrder(long orderid,long dishid);
    public void deleteOrder(long orderid);
    public Order getDraftOrderByEmail(String email);

}
