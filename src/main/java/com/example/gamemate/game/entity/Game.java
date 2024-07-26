package com.example.gamemate.game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 100)
    private String developer;

    @Column(length = 100)
    private String genre;

    @Column(length = 100)
    private String platform;

    @Column(length = 100)
    private String classes;

    @Column(length = 100)
    private String description;


    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameComment> comments;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameRating> ratings;
}
