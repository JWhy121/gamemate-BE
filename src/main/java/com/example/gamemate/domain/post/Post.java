package com.example.gamemate.domain.post;

import com.example.gamemate.global.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
@Table(name = "post")
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnOffStatus onOff;

    @Column(length = 100)
    private String gameTitle;

    @Column(length = 50)
    private String gameGenre;

    @Column
    private Integer mateCnt;

    @Column(length = 500)
    private String mateContent;

    @Column
    private Integer commentCnt;

    @Column(length = 30)
    private String mateRegion;

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime updatedDate;

    // Enum for On/Off status
    public enum OnOffStatus {
        ON, OFF
    }
}
