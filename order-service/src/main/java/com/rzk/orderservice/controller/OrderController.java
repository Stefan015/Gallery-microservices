package com.rzk.orderservice.controller;

import com.rzk.orderservice.dto.OrderRequestDto;
import com.rzk.orderservice.dto.OrderResponseDto;
import com.rzk.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto request) {
        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersForUser(userId));
    }
}