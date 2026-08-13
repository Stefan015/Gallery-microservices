package com.rzk.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExhibitionResponseDto {
    private Long id;
    private String title;
    private String venueName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private List<String> paintingTitles;
}