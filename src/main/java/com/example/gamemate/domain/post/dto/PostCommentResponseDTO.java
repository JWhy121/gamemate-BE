package com.example.gamemate.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostCommentResponseDTO {

    private Long id;

    private String nickname;

    private String content;

    private List<RecommentResponseDTO> recomments;
}
