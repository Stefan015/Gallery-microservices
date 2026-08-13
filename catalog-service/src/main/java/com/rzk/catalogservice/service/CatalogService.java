package com.rzk.catalogservice.service;

import com.rzk.catalogservice.dto.PaintingRequestDto;
import com.rzk.catalogservice.dto.PaintingResponseDto;
import com.rzk.catalogservice.model.Artist;
import com.rzk.catalogservice.model.Category;
import com.rzk.catalogservice.model.Painting;
import com.rzk.catalogservice.model.PaintingStatus;
import com.rzk.catalogservice.repository.ArtistRepository;
import com.rzk.catalogservice.repository.CategoryRepository;
import com.rzk.catalogservice.repository.PaintingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final PaintingRepository paintingRepository;
    private final ArtistRepository artistRepository;
    private final CategoryRepository categoryRepository;

    public List<PaintingResponseDto> getAllPaintings() {
        return paintingRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PaintingResponseDto getPaintingById(Long id) {
        Painting painting = paintingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found: " + id));
        return toDto(painting);
    }

    public PaintingResponseDto createPainting(PaintingRequestDto request) {
        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new IllegalArgumentException("Artist not found: " + request.getArtistId()));

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.getCategoryId()));
        }

        Painting painting = new Painting();
        painting.setTitle(request.getTitle());
        painting.setArtist(artist);
        painting.setCategory(category);
        painting.setPrice(request.getPrice());
        painting.setYearCreated(request.getYearCreated());
        painting.setStatus(PaintingStatus.AVAILABLE);

        Painting saved = paintingRepository.save(painting);
        return toDto(saved);
    }

    public void deletePainting(Long id) {
        if (!paintingRepository.existsById(id)) {
            throw new IllegalArgumentException("Painting not found: " + id);
        }
        paintingRepository.deleteById(id);
    }

    public void reserveForOrder(Long paintingId) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found: " + paintingId));

        if (painting.getStatus() != PaintingStatus.AVAILABLE) {
            throw new IllegalStateException("Painting is not available: " + painting.getStatus());
        }

        painting.setStatus(PaintingStatus.RESERVED);
        paintingRepository.save(painting);
    }

    public void releaseReservation(Long paintingId) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found: " + paintingId));

        painting.setStatus(PaintingStatus.AVAILABLE);
        paintingRepository.save(painting);
    }

    public void markSold(Long paintingId) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found: " + paintingId));

        painting.setStatus(PaintingStatus.SOLD);
        paintingRepository.save(painting);
    }

    public void markOnExhibition(Long paintingId) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found: " + paintingId));

        if (painting.getStatus() != PaintingStatus.AVAILABLE) {
            throw new IllegalStateException("Painting is not available for exhibition: " + painting.getStatus());
        }

        painting.setStatus(PaintingStatus.ON_EXHIBITION);
        paintingRepository.save(painting);
    }

    private PaintingResponseDto toDto(Painting p) {
        return new PaintingResponseDto(
                p.getId(),
                p.getTitle(),
                p.getArtist().getFirstName() + " " + p.getArtist().getLastName(),
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getPrice(),
                p.getYearCreated(),
                p.getStatus()
        );
    }
}