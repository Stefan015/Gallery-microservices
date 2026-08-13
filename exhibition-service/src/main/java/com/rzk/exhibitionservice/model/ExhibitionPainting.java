package com.rzk.exhibitionservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exhibition_paintings")
public class ExhibitionPainting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exhibition_id", nullable = false)
    private Exhibition exhibition;

    @Column(name = "painting_id", nullable = false)
    private Long paintingId;


}