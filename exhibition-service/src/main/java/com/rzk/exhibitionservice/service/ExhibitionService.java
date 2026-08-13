package com.rzk.exhibitionservice.service;

import com.rzk.exhibitionservice.client.CatalogClient;
import com.rzk.exhibitionservice.dto.PaintingDto;
import com.rzk.exhibitionservice.dto.ExhibitionRequestDto;
import com.rzk.exhibitionservice.dto.ExhibitionResponseDto;
import com.rzk.exhibitionservice.model.Exhibition;
import com.rzk.exhibitionservice.model.ExhibitionPainting;
import com.rzk.exhibitionservice.model.Venue;
import com.rzk.exhibitionservice.repository.ExhibitionPaintingRepository;
import com.rzk.exhibitionservice.repository.ExhibitionRepository;
import com.rzk.exhibitionservice.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    private final VenueRepository venueRepository;
    private final ExhibitionPaintingRepository exhibitionPaintingRepository;
    private final CatalogClient catalogClient;

    public ExhibitionResponseDto createExhibition(ExhibitionRequestDto request) {
        Venue venue = null;
        if (request.getVenueId() != null) {
            venue = venueRepository.findById(request.getVenueId())
                    .orElseThrow(() -> new IllegalArgumentException("Venue not found: " + request.getVenueId()));
        }

        Exhibition exhibition = new Exhibition();
        exhibition.setTitle(request.getTitle());
        exhibition.setVenue(venue);
        exhibition.setStartDate(request.getStartDate());
        exhibition.setEndDate(request.getEndDate());
        exhibition.setDescription(request.getDescription());

        Exhibition saved = exhibitionRepository.save(exhibition);
        return toDto(saved);
    }

    public ExhibitionResponseDto getExhibition(Long id) {
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exhibition not found: " + id));
        return toDto(exhibition);
    }

    public List<ExhibitionResponseDto> getAllExhibitions() {
        return exhibitionRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void addPaintingToExhibition(Long exhibitionId, Long paintingId) {
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("Exhibition not found: " + exhibitionId));

        PaintingDto painting = catalogClient.getPainting(paintingId);
        if (!"AVAILABLE".equals(painting.getStatus())) {
            throw new IllegalStateException("Painting is not available for exhibition: " + painting.getStatus());
        }

        catalogClient.markOnExhibition(paintingId);

        ExhibitionPainting ep = new ExhibitionPainting();
        ep.setExhibition(exhibition);
        ep.setPaintingId(paintingId);
        exhibitionPaintingRepository.save(ep);
    }

    private ExhibitionResponseDto toDto(Exhibition exhibition) {
        List<ExhibitionPainting> links = exhibitionPaintingRepository.findByExhibition_Id(exhibition.getId());
        List<String> paintingTitles = links.stream()
                .map(link -> catalogClient.getPainting(link.getPaintingId()).getTitle())
                .collect(Collectors.toList());

        return new ExhibitionResponseDto(
                exhibition.getId(),
                exhibition.getTitle(),
                exhibition.getVenue() != null ? exhibition.getVenue().getName() : null,
                exhibition.getStartDate(),
                exhibition.getEndDate(),
                exhibition.getDescription(),
                paintingTitles
        );
    }
}