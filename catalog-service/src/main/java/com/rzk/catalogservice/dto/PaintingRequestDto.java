package com.rzk.catalogservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaintingRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Artist id is required")
    private Long artistId;

    private Long categoryId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    private Integer yearCreated;
}