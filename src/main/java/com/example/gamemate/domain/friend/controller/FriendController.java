package com.example.gamemate.domain.friend.controller;

import com.example.gamemate.domain.friend.dto.FriendPostDto;
import com.example.gamemate.domain.friend.dto.FriendPutDto;
import com.example.gamemate.domain.friend.dto.FriendRequestDto;
import com.example.gamemate.domain.friend.entity.Friend;
import com.example.gamemate.domain.friend.entity.FriendId;
import com.example.gamemate.domain.friend.service.FriendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@Tag(name = "Friend", description = "친구 관리 API")
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/request")
    public ResponseEntity<FriendRequestDto> sendFriendRequest(@RequestBody FriendPostDto friendPostDto) {
        FriendRequestDto response = friendService.sendFriendRequest(friendPostDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/respond")
    public ResponseEntity<FriendRequestDto> respondToFriendRequest(@RequestBody FriendPutDto friendPutDto) {
        FriendRequestDto response = friendService.respondToFriendRequest(friendPutDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<Friend>> getFriends(@PathVariable Long userId) {
        List<Friend> friends = friendService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }
}
