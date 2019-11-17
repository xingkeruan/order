package com.edu.order.service.impl;

import com.edu.order.domain.Order;
import com.edu.order.domain.OrderDetail;
import com.edu.order.repository.OrderRepository;
import com.edu.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailService orderDetailService;


    public Order getOrderById(long id) {
        return orderRepository.findById(id).get();
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order addDishToOrder(String email, long dishid) {
        Order order = this.getDraftOrderByEmail(email);
        if(order==null) {
            order = new Order();
            order.setEmail(email);
            order.setOrderDetailList(new ArrayList<OrderDetail>());
            OrderDetail orderDetail = new OrderDetail(dishid,1);
            order.setStatus("DRAFT");
            order.getOrderDetailList().add(orderDetail);
            order = orderRepository.save(order);
            orderDetailService.addOrderDetail(orderDetail);
        }
        else{
            OrderDetail orderDetail1= null;
            for(OrderDetail orderDetail:order.getOrderDetailList()){
                if(orderDetail.getDishid()==dishid)
                    orderDetailService.dishSetCount(orderDetail.getId(),orderDetail.getCount()+1);
                else {
                    orderDetail1 = new OrderDetail(dishid,1);
//                    orderDetailService.addOrderDetail(orderDetail);
                }
            }
            if(orderDetail1!=null) {
                orderDetail1.setCount(1);
                orderDetail1.setDishid(dishid);
                order.getOrderDetailList().add(orderDetail1);
                orderRepository.save(order);
            }
        }

        return this.getDraftOrderByEmail(email);
    }

    @Override
    public Order getDraftOrderByEmail(String email) {
        List<Order> orderList = orderRepository.getOrderByEmail(email);
        if(orderList.size()>0)
            return orderList.get(0);
        return null;
    }

    @Override
    public Order createOrder() {
        return null;
    }

    @Override
    public Order addDishToOrder(long orderid, long dishid) {
        return null;
    }

    public void deleteOrder(long orderid) {

    }
}
