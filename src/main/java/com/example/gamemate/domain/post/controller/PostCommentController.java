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
        postCommentService.createPostComment(id, postCommentDTO);
        return ResponseEntity.ok().build();
    }

    //게시글 댓글 수정 api
    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Object> editComment(@PathVariable Long postId, Long commentId, @RequestBody PostCommentDTO postCommentDTO){
        postCommentService.updateComment(postId, commentId, postCommentDTO);
        return ResponseEntity.ok().build();
    }


    //게시글 댓글 삭제 api
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Object> removeComment(@PathVariable Long postId, Long commentId){
        postCommentService.deleteComment(postId, commentId);

        return ResponseEntity.ok().build();
    }

}