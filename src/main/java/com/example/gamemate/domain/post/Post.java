package com.example.gamemate.domain.post;

import com.example.gamemate.global.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;


//setter 어노테이션 허용 시 어디서는 객체의 변경이 가능하기 때문에 사용 지양하기
//대신 빌더 패턴 사용
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post extends BaseEntity {

    @Builder
    public Post(Long userId, OnOffStatus onOff, String gameTitle, String gameGenre, Integer mateCnt, String mateContent) {
        this.userId = userId;
        this.onOff = onOff;
        this.gameTitle = gameTitle;
        this.gameGenre = gameGenre;
        this.mateCnt = mateCnt;
        this.mateContent = mateContent;
    }

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

    @Column(length = 30)
    private String mateRegionSi;

    @Column(length = 30)
    private String mateRegionGu;

    @Column
    private BigDecimal latitude;

    @Column
    private BigDecimal longitude;

    // Enum for On/Off status
    public enum OnOffStatus {
        ON, OFF
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostComment> postComments;


}
