package com.example.gamemate.domain.friend.controller;

import com.example.gamemate.domain.friend.Friend;
import com.example.gamemate.domain.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/request")
    public Friend sendFriendRequest(@RequestParam Long requesterId, @RequestParam Long receiverId) {
        return friendService.sendFriendRequest(requesterId, receiverId);
    }

    @PutMapping("/{friendId}/respond")
    public Friend respondToFriendRequest(@PathVariable Long friendId, @RequestParam Friend.Status status) {
        return friendService.respondToFriendRequest(friendId, status);
    }

    @GetMapping("/{userId}/friends")
    public List<Friend> getFriends(@PathVariable Long userId) {
        return friendService.getFriends(userId);
    }
}
