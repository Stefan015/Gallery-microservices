package com.rzk.catalogservice.repository;

import com.rzk.catalogservice.model.Painting;
import com.rzk.catalogservice.model.PaintingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaintingRepository extends JpaRepository<Painting, Long> {
    List<Painting> findByStatus(PaintingStatus status);
    List<Painting> findByArtist_Id(Long artistId);
    List<Painting> findByCategory_Id(Long categoryId);
}