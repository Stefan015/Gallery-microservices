package com.rzk.exhibitionservice.client;

import com.rzk.exhibitionservice.dto.PaintingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "catalog-service")
public interface CatalogClient {

    @GetMapping("/api/catalog/paintings/{id}")
    PaintingDto getPainting(@PathVariable("id") Long paintingId);

    @PostMapping("/api/catalog/internal/paintings/{id}/mark-on-exhibition")
    void markOnExhibition(@PathVariable("id") Long paintingId);
}