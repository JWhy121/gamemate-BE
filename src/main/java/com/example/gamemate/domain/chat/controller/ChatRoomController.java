package com.example.gamemate.domain.chat.controller;

import com.example.gamemate.domain.chat.model.chatroom.ChatRoomCreateRequest;
import com.example.gamemate.domain.chat.model.chatroom.ChatRoomCreateResponse;
import com.example.gamemate.domain.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/createroom")
    public ChatRoomCreateResponse createChatRoom(@RequestBody ChatRoomCreateRequest request){
        return chatRoomService.createChatRoom(request.getChatTitle(),request.getUser());
    }
}
