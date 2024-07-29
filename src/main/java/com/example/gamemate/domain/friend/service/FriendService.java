package com.example.gamemate.domain.friend.service;

import com.example.gamemate.domain.user.User;
import com.example.gamemate.domain.friend.Friend;
import com.exmaple.gamemate.domain.user.repository.UserRepository;
import com.example.gamemate.domain.friend.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public Friend sendFriendRequest(Long requesterId, Long receiverId) {
        User requester = userRepository.findById(requesterId).orElseThrow(() -> new IllegalArgumentException("Invalid requester ID"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        Friend friend = new Friend();
        friend.setRequester(requester);
        friend.setReceiver(receiver);
        friend.setStatus(Friend.Status.PENDING);

        return friendRepository.save(friend);
    }

    public Friend respondToFriendRequest(Long friendId, Friend.Status status) {
        Friend friend = friendRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("Invalid friend ID"));
        friend.setStatus(status);

        return friendRepository.save(friend);
    }

    public List<Friend> getFriends(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return friendRepository.findByRequesterOrReceiver(user, user);
    }
}