package com.example.gamemate.domain.post.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecommentResponseDTO {

    private Long id;

    private String nickname;

    private String content;
}
