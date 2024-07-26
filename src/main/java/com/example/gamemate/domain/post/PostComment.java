package com.example.gamemate.domain.post;

import com.example.gamemate.domain.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "post_comment")
@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private Long postId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Long pCommentId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime updatedDate;

}