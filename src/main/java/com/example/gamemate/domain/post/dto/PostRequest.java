package com.example.gamemate.domain.post.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequest {

    @NotNull
    @Pattern(regexp = "on|off", message = "온오프라인을 선택해주세요.")
    private String onOff;

    @NotNull
    @Size(max = 100, message = "게임 제목은 100자 이하이어야 합니다.")
    private String gameTitle;

    @NotNull
    private Long userId;

    @NotNull
    @Size(max = 50, message = "게임 장르는 50자 이하이어야 합니다.")
    private String gameGenre;

    @NotNull
    @Min(value = 1, message = "모집 인원은 1명 이상이어야 합니다.")
    private Integer mateCnt;

    @NotNull
    @Size(max = 500, message = "내용은 500자 이하여야 합니다.")
    private String mateContent;

    private String mateRegionSi;

    private String mateRegionGu;

    private Double latitude;

    private Double longitude;
}