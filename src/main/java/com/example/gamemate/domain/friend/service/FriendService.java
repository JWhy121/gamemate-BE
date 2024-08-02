package com.example.gamemate.domain.friend.service;

import com.example.gamemate.domain.friend.dto.FriendPostDto;
import com.example.gamemate.domain.friend.dto.FriendPutDto;
import com.example.gamemate.domain.friend.dto.FriendRequestDto;
import com.example.gamemate.domain.friend.entity.Friend;
import com.example.gamemate.domain.friend.entity.FriendId;
import com.example.gamemate.domain.friend.repository.FriendRepository;
import com.example.gamemate.domain.user.entity.User;
import com.example.gamemate.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public FriendRequestDto sendFriendRequest(FriendPostDto friendPostDto) {
        User requester = userRepository.findById(friendPostDto.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid requester ID"));
        User receiver = userRepository.findById(friendPostDto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        Friend friend = new Friend();
        friend.setRequester(requester);
        friend.setReceiver(receiver);
        friend.setStatus(Friend.Status.PENDING);

        friendRepository.save(friend);

        return new FriendRequestDto("Friend request sent", friend.getStatus(), requester, receiver);
    }

    public FriendRequestDto respondToFriendRequest(FriendPutDto friendPutDto) {
        FriendId friendId = new FriendId(friendPutDto.getRequesterId(), friendPutDto.getReceiverId());
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid friend ID"));

        friend.setStatus(friendPutDto.getStatus());
        friendRepository.save(friend);

        return new FriendRequestDto("Friend request responded", friend.getStatus(), friend.getRequester(), friend.getReceiver());
    }

    public List<Friend> getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return friendRepository.findByRequesterOrReceiver(user, user);
    }

    public List<Friend> getFriendsByStatus(Long userId, Friend.Status status) {
        return friendRepository.findFriendsByStatus(userId, status);
    }
}

