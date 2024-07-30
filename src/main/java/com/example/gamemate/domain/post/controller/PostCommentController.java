package com.example.gamemate.domain.post.controller;

import com.example.gamemate.domain.post.dto.PostCommentDTO;
import com.example.gamemate.domain.post.entity.PostComment;
import com.example.gamemate.domain.post.service.PostCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Post Comment", description = "Post Comment API")
public class PostCommentController {

    private final PostCommentService postCommentService;

    public PostCommentController(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    //게시글 댓글 작성 api
    @PostMapping("/post/{id}/comment")
    public ResponseEntity<Object> registerPostComment(@PathVariable Long id, @RequestBody PostCommentDTO postCommentDTO){

        System.out.println(postCommentDTO.getPCommentId());

        postCommentService.createPostComment(id, postCommentDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test(){

        List<PostComment> offlinePosts = postCommentService.test(1L);

        return ResponseEntity.ok(offlinePosts);
    }
}