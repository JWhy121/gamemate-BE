package com.example.gamemate.domain.friend.repository;

import com.example.gamemate.domain.friend.entity.Friend;
import com.example.gamemate.domain.friend.entity.QFriend;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendRepositoryCustomImpl implements FriendRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<Friend> findFriendsByStatus(Long userId, Friend.Status status) {
        QFriend friend = QFriend.friend;

        return queryFactory
                .selectFrom(friend)
                .where((friend.requester.id.eq(userId).or(friend.receiver.id.eq(userId)))
                        .and(friend.status.eq(status)))
                .fetch();
    }
}

