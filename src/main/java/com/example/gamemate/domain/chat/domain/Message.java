package com.example.gamemate.domain.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

//    @ManyToOne
//    @JoinColumn(name = "chat_room")
//    private ChatRoom chatRoom;

    private String chatUuid;
    // 음 이거를 쓰게 되니까 연관관계 매핑을 못하게 되버리네... 흠....

//    @ManyToOne
//    @JoinColumn(name = "writer")
//    private ChatTestUser writer;

    // 테스트용인 String 유저.
    private String writer;

    public Message(String content, String chatUuid, String writer) {
        this.content = content;
        this.chatUuid = chatUuid;
        this.writer = writer;
    }
}
