package com.example.gamemate.domain.chat.service;

import com.example.gamemate.domain.chat.domain.ChatRoom;
import com.example.gamemate.domain.chat.domain.Message;
import com.example.gamemate.domain.chat.repository.ChatRoomRepository;
import com.example.gamemate.domain.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    public void saveMessage(Long chatRoomId, String content, String writer){


        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);


        Message message = new Message(content,chatRoom,writer);

        messageRepository.save(message);
    }

}
