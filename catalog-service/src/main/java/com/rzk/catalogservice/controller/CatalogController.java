package com.rzk.catalogservice.controller;

import com.rzk.catalogservice.dto.PaintingRequestDto;
import com.rzk.catalogservice.dto.PaintingResponseDto;
import com.rzk.catalogservice.service.CatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/paintings")
    public ResponseEntity<List<PaintingResponseDto>> getAllPaintings() {
        return ResponseEntity.ok(catalogService.getAllPaintings());
    }

    @GetMapping("/paintings/{id}")
    public ResponseEntity<PaintingResponseDto> getPainting(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getPaintingById(id));
    }


    @PostMapping("/paintings")
    public ResponseEntity<PaintingResponseDto> createPainting(@Valid @RequestBody PaintingRequestDto request) {
        return ResponseEntity.ok(catalogService.createPainting(request));
    }

    @DeleteMapping("/paintings/{id}")
    public ResponseEntity<Void> deletePainting(@PathVariable Long id) {
        catalogService.deletePainting(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/internal/paintings/{id}/reserve")
    public ResponseEntity<Void> reserveForOrder(@PathVariable Long id) {
        catalogService.reserveForOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/internal/paintings/{id}/release")
    public ResponseEntity<Void> releaseReservation(@PathVariable Long id) {
        catalogService.releaseReservation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/internal/paintings/{id}/mark-sold")
    public ResponseEntity<Void> markSold(@PathVariable Long id) {
        catalogService.markSold(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/internal/paintings/{id}/mark-on-exhibition")
    public ResponseEntity<Void> markOnExhibition(@PathVariable Long id) {
        catalogService.markOnExhibition(id);
        return ResponseEntity.ok().build();
    }
}