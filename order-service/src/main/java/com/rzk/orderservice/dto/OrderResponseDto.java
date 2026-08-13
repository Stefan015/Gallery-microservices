package com.rzk.orderservice.dto;

import com.rzk.orderservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<Long> paintingIds;
}