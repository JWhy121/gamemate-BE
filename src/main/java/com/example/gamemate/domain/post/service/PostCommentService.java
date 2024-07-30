package com.example.gamemate.domain.post.service;

import com.example.gamemate.domain.post.entity.Post;
import com.example.gamemate.domain.post.entity.PostComment;
import com.example.gamemate.domain.post.dto.PostCommentDTO;
import com.example.gamemate.domain.post.repository.PostCommentRepository;
import com.example.gamemate.domain.post.repository.PostRepository;
import com.example.gamemate.global.exception.PostExceptionCode;
import com.example.gamemate.global.exception.RestApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    private final PostRepository postRepository;

    public PostCommentService(PostCommentRepository postCommentRepository, PostRepository postRepository) {
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
    }

    public void createPostComment(Long id, PostCommentDTO postCommentDTO){

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RestApiException(PostExceptionCode.POST_NOT_FOUND));

        System.out.println("부모 아이디 : " + postCommentDTO.getPCommentId());

        //대댓글일 경우 실행되는 로직
        PostComment parentComment = null;
        if(postCommentDTO.getPCommentId() != null){
            System.out.println(postCommentDTO.getPCommentId());
            parentComment = postCommentRepository.findById(postCommentDTO.getPCommentId())
                    .orElseThrow(() -> new RestApiException(PostExceptionCode.COMMENT_NOT_FOUND));
        }
        ;

        PostComment postComment = PostComment.builder()
                .post(post)
                .parentComment(parentComment)
                .nickname(postCommentDTO.getNickname())
                .content(postCommentDTO.getContent())
                .build();

        postCommentRepository.save(postComment);
    }

    public List<PostComment> test(Long id){

        List<PostComment> post = postCommentRepository.findByidAndParentCommentIsNull(id);

        return post;
    }
}
