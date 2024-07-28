package com.example.gamemate.domain.post.dto;

import com.example.gamemate.domain.post.Post;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OfflinePostResponse {

    private final String status;
    private final String gameTitle;
    private final String gameGenre;
    private final Long userId;
    private final Integer mateCnt;
    private final String mateContent;
    private final String mateRegionSi;
    private final String mateRegionGu;
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public OfflinePostResponse(Post post) {
        this.status = post.getStatus().toString();
        this.gameTitle = post.getGameTitle();
        this.gameGenre = post.getGameGenre();
        this.userId = post.getUserId();
        this.mateCnt = post.getMateCnt();
        this.mateContent = post.getMateContent();
        this.mateRegionGu = post.getMateRegionGu();
        this.mateRegionSi = post.getMateRegionSi();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();

    }
}
