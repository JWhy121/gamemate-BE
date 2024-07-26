package com.example.gamemate.game.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import lombok.*;

@Table(name = "game_rating")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class GameRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private int rating;

    //rating은 1~10의 int -> 프론트에서는 1~5 사이의 값으로 나타나도록 나누기 2
    public void setRating(int rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("평점은 1에서 10 사이의 정수여야 합니다.");
        }
        this.rating = rating;
    }

}
