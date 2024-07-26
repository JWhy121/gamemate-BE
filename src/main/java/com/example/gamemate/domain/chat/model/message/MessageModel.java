package com.example.gamemate.domain.chat.model.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
    private String writer;
    private String content;
    private String chatUuid;

    public MessageModel(String writer, String content, String chatUuid) {
        this.writer = writer;
        this.content = content;
        this.chatUuid = chatUuid;
    }
}
