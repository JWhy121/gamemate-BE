package com.example.gamemate.domain.chat.converter;

import com.example.gamemate.domain.chat.domain.ChatRoom;
import com.example.gamemate.domain.chat.dto.chatroom.ChatRoomDTO;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomConverter {
    public ChatRoomDTO convertoToChatRoomDTO(ChatRoom chatRoom){
        return new ChatRoomDTO(chatRoom.getId(),chatRoom.getTitle(), chatRoom.getLeader());
    }
}
