package com.example.gamemate.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OfflinePostRequest extends PostRequest{

    public OfflinePostRequest() {
        super.setStatus("OFF");
    }

    private String mateRegionSi;
    private String mateRegionGu;
    private BigDecimal latitude;
    private BigDecimal longitude;

}