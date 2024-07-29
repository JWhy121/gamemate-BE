package com.example.gamemate.domain.friend.repository;

import com.example.gamemate.domain.friend.entity.Friend;
import com.example.gamemate.domain.friend.entity.FriendId;
import com.example.gamemate.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    List<Friend> findByRequesterOrReceiver(User requester, User receiver);
}
