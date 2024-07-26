package com.example.gamemate.domain.chat.service;

import com.example.gamemate.domain.chat.domain.Message;
import com.example.gamemate.domain.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public void saveMessage(String chatUuid, String content, String writer){
        Message message = new Message(content,chatUuid,writer);

        messageRepository.save(message);
    }

}
