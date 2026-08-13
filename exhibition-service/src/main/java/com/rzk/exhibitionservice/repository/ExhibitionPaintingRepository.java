package com.rzk.exhibitionservice.repository;

import com.rzk.exhibitionservice.model.ExhibitionPainting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitionPaintingRepository extends JpaRepository<ExhibitionPainting, Long> {
    List<ExhibitionPainting> findByExhibition_Id(Long exhibitionId);
}