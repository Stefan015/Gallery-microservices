package com.rzk.catalogservice.dto;

import com.rzk.catalogservice.model.PaintingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaintingResponseDto {
    private Long id;
    private String title;
    private String artistName;
    private String categoryName;
    private BigDecimal price;
    private Integer yearCreated;
    private PaintingStatus status;
}