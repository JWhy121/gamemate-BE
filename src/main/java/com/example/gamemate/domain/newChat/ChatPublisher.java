package com.example.gamemate.domain.newChat;

import com.example.gamemate.domain.chat.model.message.OutputMessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public ChatPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publishChat(String roomId, OutputMessageModel outputMessageModel){
        redisTemplate.convertAndSend("chatroom:" + roomId, outputMessageModel);
    }

}
