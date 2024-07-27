package com.example.gamemate.domain.post.service;

import com.example.gamemate.domain.post.Post;
import com.example.gamemate.domain.post.dto.PostRequest;
import com.example.gamemate.domain.post.dto.PostResponse;
import com.example.gamemate.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Transactional
    public PostResponse createPost(PostRequest postRequest) {

        Post post = Post.builder()
                .onOff(Post.OnOffStatus.valueOf(postRequest.getOnOff().toUpperCase()))
                .gameTitle(postRequest.getGameTitle())
                .gameGenre(postRequest.getGameGenre())
                .userId(postRequest.getUserId())
                .mateCnt(postRequest.getMateCnt())
                .mateContent(postRequest.getMateContent())
                .build();

        return postRepository.save(post);
    }



}