package com.example.gamemate.domain.post.repository;

import com.example.gamemate.domain.post.entity.PostComment;

import java.util.List;

public interface PostCommentCustomRepository {

    List<PostComment> findPostCommentsById(Long id);

}