package com.example.gamemate.domain.post.controller;


import com.example.gamemate.domain.post.Post;
import com.example.gamemate.domain.post.dto.OfflinePostResponse;
import com.example.gamemate.domain.post.dto.OnlinePostRequest;
import com.example.gamemate.domain.post.dto.PostRequest;
import com.example.gamemate.domain.post.dto.OnlinePostResponse;
import com.example.gamemate.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@Validated
@Tag(name = "Post", description = "Post API")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }


    //온라인 글 List 조회 api
    @GetMapping("/online")
    public ResponseEntity<Page<OnlinePostResponse>> getAllOnlinePosts(@PageableDefault(size = 10) Pageable pageable){

        Page<OnlinePostResponse> onlinePosts = postService.readPostsOnline(pageable);

        return ResponseEntity.ok(onlinePosts);
    }

    //오프라인 글 List 조회 api
    @GetMapping("/offline")
    public ResponseEntity<Page<OfflinePostResponse>> getAllOfflinePosts(@PageableDefault(size = 10) Pageable pageable){

        Page<OfflinePostResponse> offlinePosts = postService.readPostsOffline(pageable);

        return ResponseEntity.ok(offlinePosts);
    }

    //글 조회 api
    @GetMapping("/post/{id}")
    public ResponseEntity<Object> getOnlinePost(@PathVariable Long id){
        OfflinePostResponse post = postService.readPost(id);
        return ResponseEntity.ok(post);
    }

    //글 작성 api
    @PostMapping("/post/create")
    public ResponseEntity<Object> registerPost(@Valid @RequestBody PostRequest postRequest){

        postService.createPost(postRequest);

        // 생성된 게시글 정보 반환
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //글 수정 api
    @PutMapping("/post/{id}")
    public ResponseEntity<Object> editPost(@Valid @PathVariable Long id, @RequestBody PostRequest postRequest){

        postService.updatePost(id, postRequest);

        return ResponseEntity.ok().build();
    }


}
