package com.example.gamemate.domain.chat.service;


import com.example.gamemate.domain.chat.mapper.ChatRoomMapper;
import com.example.gamemate.domain.chat.domain.ChatRoom;
import com.example.gamemate.domain.chat.dto.ChatRoomDTO;
import com.example.gamemate.domain.chat.model.chatroom.ChatRoomCreateRequest;
import com.example.gamemate.domain.chat.model.chatroom.ChatRoomCreateResponse;
import com.example.gamemate.domain.chat.model.chatroommember.AddMemberRequest;
import com.example.gamemate.domain.chat.repository.ChatRoomRepository;
import com.example.gamemate.domain.user.entity.User;
import com.example.gamemate.domain.user.repository.UserRepository;
import com.example.gamemate.global.exception.ChatExceptionCode;
import com.example.gamemate.global.exception.ChatRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberService chatRoomMemberService;
    private final ChatRoomMapper chatRoomMapper;
    private final UserRepository userRepository;


    public List<ChatRoomDTO> getAllChatRoomsByUser(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUser(user);

        return chatRooms.stream()
                .map(chatRoomMapper::convertoToChatRoomDTO)
                .collect(Collectors.toList());
    }

    public void deleteChatRoom(Long roomId){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(()->new ChatRoomException(ChatExceptionCode.CHATROOM_NOT_FOUND));

        chatRoomRepository.deleteById(roomId);

    }

    public Long getChatRoomMemberCnt(Long roomId){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(()
                -> new ChatRoomException(ChatExceptionCode.CHATROOM_NOT_FOUND));


            return chatRoomRepository.countByChatRoom(chatRoom);

    }
}
