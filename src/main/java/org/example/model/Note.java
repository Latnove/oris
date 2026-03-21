package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(length = 1024,  nullable = false)
    private String content;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private Boolean isPublic = true;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
