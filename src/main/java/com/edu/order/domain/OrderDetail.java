package com.edu.order.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Setter
@Getter
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long dishid;
    int count;

    public OrderDetail(long dishid, int count) {
        this.dishid = dishid;
        this.count = count;
    }

    public OrderDetail() {
    }
}
