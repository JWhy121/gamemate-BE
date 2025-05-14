package com.example.gamemate.domain.newChat;

import com.example.gamemate.domain.chat.model.message.OutputMessageModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RedisMessageListener implements MessageListener {

    private final SimpMessageSendingOperations messagingTemplate;

    public RedisMessageListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String payload = new String(message.getBody());
        String channel = new String(pattern);

        String roomId = channel.split(":")[1];

        OutputMessageModel outputMessageModel = convertJsonToOutputMessage(payload);

        messagingTemplate.convertAndSend("/topic/chat/" + outputMessageModel.getChatRoomId(), outputMessageModel);
    }



    // JSON 문자열을 OutputMessageModel로 변환하는 메서드 (필요시 구현)
    private OutputMessageModel convertJsonToOutputMessage(String json) {
        // 여기에서 JSON을 OutputMessageModel로 변환하는 로직을 구현합니다.
        // 예를 들어, Jackson 라이브러리나 Gson 등을 사용할 수 있습니다.
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, OutputMessageModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
