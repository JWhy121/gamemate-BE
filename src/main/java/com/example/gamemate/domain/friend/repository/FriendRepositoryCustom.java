package com.example.gamemate.domain.friend.repository;

import com.example.gamemate.domain.friend.entity.Friend;
import java.util.List;

public interface FriendRepositoryCustom {
    List<Friend> findFriendsByStatus(Long userId, Friend.Status status);
}
