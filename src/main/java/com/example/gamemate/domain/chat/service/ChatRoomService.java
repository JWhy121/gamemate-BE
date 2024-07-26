package com.example.gamemate.domain.chat.service;

import com.example.gamemate.domain.chat.domain.ChatRoom;
import com.example.gamemate.domain.chat.model.ChatRoomCreateResponse;
import com.example.gamemate.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomCreateResponse createChatRoom(String chatTitle, String leader){

        String chatUuid = UUID.randomUUID().toString();
        ChatRoom chatRoom = new ChatRoom(chatUuid, chatTitle, leader);

        chatRoomRepository.save(chatRoom);

        return ChatRoomCreateResponse.from(true, "채팅방을 생성하였습니다.");
    }
}
