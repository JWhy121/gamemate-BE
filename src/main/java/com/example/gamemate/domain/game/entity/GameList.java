package com.example.gamemate.domain.game.entity;
import com.example.gamemate.domain.user.User;
import com.example.gamemate.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

}
