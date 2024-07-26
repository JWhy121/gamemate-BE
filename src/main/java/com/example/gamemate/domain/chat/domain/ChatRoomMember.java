package com.example.gamemate.domain.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "member")
    private ChatTestUser member;
}
