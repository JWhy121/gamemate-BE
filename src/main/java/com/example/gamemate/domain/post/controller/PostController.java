package com.example.gamemate.domain.post.controller;


import com.example.gamemate.domain.post.dto.PostDTO;
import com.example.gamemate.domain.post.dto.PostUpdateDTO;
import com.example.gamemate.domain.post.dto.PostUpdateResponseDTO;
import com.example.gamemate.domain.post.dto.PostResponseDTO;
import com.example.gamemate.domain.post.service.PostService;
import com.example.gamemate.global.apiRes.ApiResponse;
import com.example.gamemate.global.common.CustomPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Post", description = "게임메이트 모집 게시글 API")
@Slf4j
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }


    //글 List 조회 api
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ApiResponse<CustomPage<PostResponseDTO>> getAllPosts(
            @RequestParam(name = "status") String status,
            @PageableDefault(size = 10) Pageable pageable
    ){

        CustomPage<PostResponseDTO> posts = postService.readPosts(status, pageable);

        return ApiResponse.successRes(HttpStatus.OK,posts);
    }

    //User 글 List 조회 api
    @GetMapping("/user")
    public ApiResponse<CustomPage<PostResponseDTO>> getUserPosts(
        @RequestParam(name = "userId") Long userId,  // 사용자 ID
        @PageableDefault(size = 3) Pageable pageable
    ) {
        CustomPage<PostResponseDTO> posts = postService.readPostsByUserId(userId, pageable); // 사용자 ID에 따른 메소드 호출
        return ApiResponse.successRes(HttpStatus.OK, posts);
    }

    //글 조회 api
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ApiResponse<PostResponseDTO> getPostWithComments(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        PostResponseDTO post = postService.readPost(userDetails.getUsername(), id);

        return ApiResponse.successRes(HttpStatus.OK,post);
    }

    //글 작성 api
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ApiResponse<PostResponseDTO> registerPost(
            @Valid @RequestBody PostDTO postDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ){

        PostResponseDTO post = postService.createPost(userDetails.getUsername(), postDTO);

        return ApiResponse.successRes(HttpStatus.OK,post);
    }

    //글 수정 api
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ApiResponse<PostUpdateResponseDTO> editPost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateDTO postUpdateDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ){

        PostUpdateResponseDTO post = postService.updatePost(userDetails.getUsername(), id, postUpdateDTO);

        return ApiResponse.successRes(HttpStatus.OK,post);
    }

    //글 삭제 api
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ApiResponse<Long> removePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ){

        postService.deletePost(userDetails.getUsername(), id);

        return ApiResponse.successRes(HttpStatus.NO_CONTENT, id);
    }

}