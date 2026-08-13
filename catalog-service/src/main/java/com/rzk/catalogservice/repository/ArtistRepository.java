package com.rzk.catalogservice.repository;

import com.rzk.catalogservice.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}