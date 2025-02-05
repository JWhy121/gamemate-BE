package com.example.gamemate.domain.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendPostDTO {

    @Schema(description = "친구 요청을 보낸 사용자 ID", example = "1")
    private Long requesterId;

    @Schema(description = "친구 요청을 받은 사용자 ID", example = "2")
    private Long receiverId;
}

