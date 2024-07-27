package com.example.gamemate.domain.post.controller;


import com.example.gamemate.domain.post.Post;
import com.example.gamemate.domain.post.dto.PostRequest;
import com.example.gamemate.domain.post.dto.PostResponse;
import com.example.gamemate.domain.post.service.PostService;
import com.example.gamemate.global.error.PostErrorCode;
import com.example.gamemate.global.error.RestApiException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@Validated
@Tag(name = "Post", description = "Post API")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<Object> getPost(){

        throw new RestApiException(PostErrorCode.POST_NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> registerPost(@Valid @RequestBody PostRequest postRequest){

        postService.createPost(postRequest);

        // 생성된 게시글 정보 반환
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
