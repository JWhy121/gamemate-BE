package com.example.gamemate.domain.newChat;


import com.example.gamemate.domain.chat.entity.Message;
import com.example.gamemate.domain.chat.model.message.MessageModel;
import com.example.gamemate.domain.chat.model.message.OutputMessageModel;
import com.example.gamemate.domain.chat.service.MessageService;
import com.example.gamemate.domain.user.dto.MyPageResponseDTO;
import com.example.gamemate.domain.user.service.UserService;
import com.example.gamemate.global.exception.AuthExceptionCode;
import com.example.gamemate.global.exception.ChatExceptionCode;
import com.example.gamemate.global.exception.ChatRoomException;
import com.example.gamemate.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class NewMessageController {

    private final ChatPublisher chatPublisher;
    private final MessageService messageService;
    private final UserService userService;


    public NewMessageController(ChatPublisher chatPublisher, MessageService messageService, UserService userService) {
        this.chatPublisher = chatPublisher;
        this.messageService = messageService;
        this.userService = userService;
    }

    @MessageMapping("/message/send/{roomId}")
    public void publishMessage(
            @Payload MessageModel messageModel,
            @DestinationVariable String roomId,
            SimpMessageHeaderAccessor headerAccessor
    ){
        log.info("publishMessage 메서드 시작. roomId: {}, messageModel: {}", roomId, messageModel);

        final String time = new SimpleDateFormat("HH:mm").format(new Date());

        Authentication auth = (Authentication) headerAccessor.getUser();
        if(auth == null) throw new RestApiException(AuthExceptionCode.USER_NOT_FOUND);
        String username = (String) auth.getPrincipal();
        MyPageResponseDTO user = userService.findByUsernameForMyPage(username);

        // DB에 저장
        Message savedMessage = messageService.saveMessage(
                messageModel.getChatRoomId(),
                messageModel.getContent(),
                username,
                time,
                messageModel.getType()
        );

        log.info("DB에 메시지 저장 완료. 메시지 ID: {}", savedMessage.getId());

        // OutputMessageModel로 변환
        OutputMessageModel output = new OutputMessageModel(
                savedMessage.getId(),
                user.getNickname(),
                messageModel.getChatRoomId(),
                messageModel.getContent(),
                time,
                messageModel.getType(),
                savedMessage.getWriter().getId(),
                savedMessage.getWriter().getUserProfile()
        );

        log.info("Redis에 메시지 발행 시작. roomId: {}", roomId);
        chatPublisher.publishChat(roomId, output);
        log.info("Redis에 메시지 발행 완료.");
    }
}
