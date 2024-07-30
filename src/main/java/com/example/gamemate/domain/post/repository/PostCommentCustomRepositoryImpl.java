package com.example.gamemate.domain.post.repository;

import com.example.gamemate.domain.post.entity.PostComment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.gamemate.domain.post.entity.QPostComment.postComment;

public class PostCommentCustomRepositoryImpl implements PostCommentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public PostCommentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PostComment> findPostCommentsById(Long id) {
        return jpaQueryFactory.selectFrom(postComment)
                .where(postComment.post.id.eq(id)
                        .and(postComment.parentComment.isNull()))
                .fetch();
    }
}