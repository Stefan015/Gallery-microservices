package com.rzk.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Username is required")
    private String username;

    @NotEmpty(message = "At least one painting must be included in the order")
    private List<Long> paintingIds;
}