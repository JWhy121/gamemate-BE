package com.example.gamemate.domain.post.repository;

import com.example.gamemate.domain.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.example.gamemate.domain.post.entity.QPost.post;


@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> findBusanTest() {
        return jpaQueryFactory.selectFrom(post)
                .where(post.mateRegionSi.startsWith("부산"))
                .fetch();
    }
}
