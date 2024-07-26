package com.example.gamemate.domain.chat.controller;

import com.example.gamemate.domain.chat.model.message.MessageModel;
import com.example.gamemate.domain.chat.model.message.OutputMessageModel;
import com.example.gamemate.domain.chat.service.MessageService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Controller
public class MessageController {

    //      클라이언트에서 보내오는 메시지모델
//    let chatMessage = {
//              sender: sessionStorage.getItem("username"),
//              content: messageInput.value,
//              chatUuid: chatUuid,
//              type: 'CHAT'
//      };
//

    private final MessageService messageService;

    @MessageMapping("/message/send/{chatUuid}") // "/app/message/send/"+chatUuid 클라이언트쪽에서 메시지를 보내오는 경로
    @SendTo("/topic/chat/{chatUuid}") // 특정 목적지를 설정. 메시지브로커는 이 경로에 해당하는 응답채널을 통해서 구독자에게 메시지를 전달할 수 있게 된다.
    public OutputMessageModel sendMessage(@Payload MessageModel messageModel, @DestinationVariable String chatUuid) { // chatUuid 이 경로로부터 데이터를 추출할 수 있음. 그러기 위해서 @DestinationVariable 사용
        final String time = new SimpleDateFormat("HH:mm").format(new Date());

        messageService.saveMessage(messageModel.getChatUuid(),messageModel.getContent(),messageModel.getWriter());

        return new OutputMessageModel(messageModel.getWriter(), messageModel.getChatUuid(), messageModel.getContent(), time, OutputMessageModel.MessageType.CHAT);

    }
    // MessageMapping의 경로로 클라이언트가 메시지를 보내오면 sendframe에 담사어 전송함.
//    위 메소드로 그 메시지를 가공해서 리턴하면 응답채널을 통해서 메시지브로커가 받음
    // 그 메시지 브로커는  그 경로를 인지한다음에 클라이언트에게 보내준다.

}
