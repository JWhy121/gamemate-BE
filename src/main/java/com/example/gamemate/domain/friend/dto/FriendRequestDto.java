package com.example.gamemate.domain.friend.dto;

import com.example.gamemate.domain.friend.entity.Friend.Status;
import com.example.gamemate.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {

    private String message;
    private Status status;
    private User requester;
    private User receiver;
}

