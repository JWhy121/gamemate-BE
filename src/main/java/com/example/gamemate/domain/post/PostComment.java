package com.example.gamemate.domain.post;

import com.example.gamemate.global.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "post_comment")
@Entity
public class PostComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Long pCommentId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private String content;
}