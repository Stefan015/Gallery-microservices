package com.rzk.orderservice.service;

import com.rzk.orderservice.client.CatalogClient;
import com.rzk.orderservice.dto.OrderRequestDto;
import com.rzk.orderservice.dto.OrderResponseDto;
import com.rzk.orderservice.dto.PaintingDto;
import com.rzk.orderservice.model.Order;
import com.rzk.orderservice.model.OrderItem;
import com.rzk.orderservice.model.OrderStatus;
import com.rzk.orderservice.model.User;
import com.rzk.orderservice.repository.OrderRepository;
import com.rzk.orderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CatalogClient catalogClient;

    public OrderResponseDto placeOrder(OrderRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseGet(() -> {
                    User u = new User();
                    u.setId(request.getUserId());
                    u.setUsername(request.getUsername());
                    return userRepository.save(u);
                });

        List<Long> reservedSoFar = new ArrayList<>();

        try {
            for (Long paintingId : request.getPaintingIds()) {
                catalogClient.reserveForOrder(paintingId);
                reservedSoFar.add(paintingId);
            }
        } catch (Exception e) {
            for (Long paintingId : reservedSoFar) {
                catalogClient.releaseReservation(paintingId);
            }
            throw new IllegalStateException("Could not complete order — a painting became unavailable: " + e.getMessage());
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(Instant.now());

        BigDecimal total = BigDecimal.ZERO;
        for (Long paintingId : request.getPaintingIds()) {
            PaintingDto painting = catalogClient.getPainting(paintingId);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setPaintingId(paintingId);
            item.setPriceAtOrderTime(painting.getPrice());
            order.getItems().add(item);

            total = total.add(painting.getPrice());
        }
        order.setTotalPrice(total);

        Order saved = orderRepository.save(order);

        return toDto(saved);
    }

    public List<OrderResponseDto> getOrdersForUser(Long userId) {
        return orderRepository.findByUser_Id(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto toDto(Order order) {
        List<Long> paintingIds = order.getItems().stream()
                .map(OrderItem::getPaintingId)
                .collect(Collectors.toList());
        return new OrderResponseDto(order.getId(), order.getStatus(), order.getTotalPrice(), paintingIds);
    }
}