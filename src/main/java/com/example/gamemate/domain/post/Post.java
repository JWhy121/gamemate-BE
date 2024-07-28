package com.example.gamemate.domain.post;

import com.example.gamemate.global.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringTokenizer;


//setter 어노테이션 허용 시 어디서는 객체의 변경이 가능하기 때문에 사용 지양하기
//대신 빌더 패턴 사용
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post extends BaseEntity {

    @Builder
    public Post(Long id, Long userId, OnOffStatus status, String gameTitle, String gameGenre, Integer mateCnt, String mateContent,
                String mateRegionSi, String mateRegionGu, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.gameTitle = gameTitle;
        this.gameGenre = gameGenre;
        this.mateCnt = mateCnt;
        this.mateContent = mateContent;
        this.mateRegionSi = mateRegionSi;
        this.mateRegionGu = mateRegionGu;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private OnOffStatus status;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostComment> postComments;

    public enum OnOffStatus {
        ON,  // 온라인
        OFF  // 오프라인
    }

    public void updatePost(Integer mateCnt, String mateContent){
        this.mateCnt = mateCnt;
        this.mateContent = mateContent;
    }

}
