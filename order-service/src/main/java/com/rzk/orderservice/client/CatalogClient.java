package com.rzk.orderservice.client;

import com.rzk.orderservice.dto.PaintingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "catalog-service")
public interface CatalogClient {

    @GetMapping("/api/catalog/paintings/{id}")
    PaintingDto getPainting(@PathVariable("id") Long paintingId);

    @PostMapping("/api/catalog/internal/paintings/{id}/reserve")
    void reserveForOrder(@PathVariable("id") Long paintingId);

    @PostMapping("/api/catalog/internal/paintings/{id}/release")
    void releaseReservation(@PathVariable("id") Long paintingId);

    @PostMapping("/api/catalog/internal/paintings/{id}/mark-sold")
    void markSold(@PathVariable("id") Long paintingId);
}