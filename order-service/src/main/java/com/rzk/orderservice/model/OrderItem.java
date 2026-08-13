package com.rzk.orderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "painting_id", nullable = false)
    private Long paintingId;

    @Column(name = "price_at_order_time", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtOrderTime;
}