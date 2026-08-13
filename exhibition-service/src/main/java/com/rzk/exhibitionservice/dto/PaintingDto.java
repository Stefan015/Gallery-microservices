package com.rzk.exhibitionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaintingDto {
    private Long id;
    private String title;
    private String artistName;
    private String categoryName;
    private BigDecimal price;
    private Integer yearCreated;
    private String status;
}