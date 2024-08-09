package com.example.gamemate.domain.game.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private String username;  // Assuming you'd want to display the username
}