package com.example.gamemate.domain.post.dto;

import com.example.gamemate.domain.post.Post;
import lombok.Getter;

@Getter
public class OnlinePostResponse {

    private final String status;
    private final String gameTitle;
    private final String gameGenre;
    private final Long userId;
    private final Integer mateCnt;
    private final String mateContent;

    public OnlinePostResponse(Post post) {
        this.status = post.getStatus().toString();
        this.gameTitle = post.getGameTitle();
        this.gameGenre = post.getGameGenre();
        this.userId = post.getUserId();
        this.mateCnt = post.getMateCnt();
        this.mateContent = post.getMateContent();

    }

}