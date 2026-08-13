package com.rzk.exhibitionservice.controller;

import com.rzk.exhibitionservice.dto.ExhibitionRequestDto;
import com.rzk.exhibitionservice.dto.ExhibitionResponseDto;
import com.rzk.exhibitionservice.service.ExhibitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @GetMapping
    public ResponseEntity<List<ExhibitionResponseDto>> getAllExhibitions() {
        return ResponseEntity.ok(exhibitionService.getAllExhibitions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExhibitionResponseDto> getExhibition(@PathVariable Long id) {
        return ResponseEntity.ok(exhibitionService.getExhibition(id));
    }

    @PostMapping
    public ResponseEntity<ExhibitionResponseDto> createExhibition(@Valid @RequestBody ExhibitionRequestDto request) {
        return ResponseEntity.ok(exhibitionService.createExhibition(request));
    }

    @PostMapping("/{exhibitionId}/paintings/{paintingId}")
    public ResponseEntity<Void> addPaintingToExhibition(
            @PathVariable Long exhibitionId,
            @PathVariable Long paintingId) {
        exhibitionService.addPaintingToExhibition(exhibitionId, paintingId);
        return ResponseEntity.ok().build();
    }
}