package com.example.gamemate.domain.post.controller;


import com.example.gamemate.domain.post.Post;
import com.example.gamemate.domain.post.service.PostService;
import com.example.gamemate.global.error.PostErrorCode;
import com.example.gamemate.global.error.RestApiException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Validated
@Tag(name = "Post", description = "Post API")
public class PostController {

    private final PostService postService;


    @GetMapping
    public ResponseEntity<Post> getPost(){

        throw new RestApiException(PostErrorCode.POST_NOT_FOUND);
    }
}
