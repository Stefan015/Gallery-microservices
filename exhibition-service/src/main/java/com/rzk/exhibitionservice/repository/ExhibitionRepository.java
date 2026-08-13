package com.rzk.exhibitionservice.repository;

import com.rzk.exhibitionservice.model.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
}