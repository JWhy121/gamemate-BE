package com.example.gamemate.domain.post.dto;

import com.example.gamemate.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OnlinePostRequest extends PostRequest {

    public OnlinePostRequest() {
        super.setStatus("ON");
    }
}
