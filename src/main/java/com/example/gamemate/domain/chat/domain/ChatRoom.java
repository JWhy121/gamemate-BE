package com.example.gamemate.domain.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatUuid;
    private String title;


    @OneToMany(mappedBy= "ChatRoom", cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "leader")
//    private ChatTestUser leader;

    // 테스트용에서는 유저를 스트링으로 받음.
    private String leader;

    public ChatRoom(String chatUuid, String title, String leader) {
        this.chatUuid = chatUuid;
        this.title = title;
        this.leader = leader;
    }
}
