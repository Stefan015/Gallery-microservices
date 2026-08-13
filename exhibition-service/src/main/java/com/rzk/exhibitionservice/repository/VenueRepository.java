package com.rzk.exhibitionservice.repository;

import com.rzk.exhibitionservice.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}