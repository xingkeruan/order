package com.edu.order.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Setter
@Getter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String email;
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="order_id")
    List<OrderDetail> orderDetailList;
    String status;
}
