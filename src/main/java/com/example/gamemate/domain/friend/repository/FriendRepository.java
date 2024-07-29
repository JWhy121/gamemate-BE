package com.example.gamemate.domain.friend.repository;

import com.example.gamemate.domain.friend.Friend;
import com.example.gamemate.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByRequesterOrReceiver(User requester, User receiver);
}