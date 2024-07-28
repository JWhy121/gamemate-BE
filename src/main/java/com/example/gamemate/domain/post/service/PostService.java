package com.example.gamemate.domain.post.service;

import com.example.gamemate.domain.post.Post;
import com.example.gamemate.domain.post.dto.*;
import com.example.gamemate.domain.post.mapper.PostMapper;
import com.example.gamemate.domain.post.repository.PostRepository;
import com.example.gamemate.global.error.PostErrorCode;
import com.example.gamemate.global.error.RestApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper mapper;



    public PostService(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public Page<OnlinePostResponse> readPostsOnline(Pageable pageable){
        Page<Post> postPage = postRepository.findByStatus(Post.OnOffStatus.ON, pageable);

        return postPage.map(OnlinePostResponse::new);
    }

    public Page<OfflinePostResponse> readPostsOffline(Pageable pageable){
        Page<Post> postPage = postRepository.findByStatus(Post.OnOffStatus.OFF, pageable);

        return postPage.map(OfflinePostResponse::new);
    }

    public OfflinePostResponse readPost(Long id){
        return postRepository.findById(id)
                .map(OfflinePostResponse::new)
                .orElseThrow(() -> new RestApiException(PostErrorCode.POST_NOT_FOUND));
    }


    //Status 값에 따라서 다른 로직 처리
    public Post createPost(PostRequest postRequest) {

        if ("ON".equals(postRequest.getStatus())) {
            return handleOnlinePost((OnlinePostRequest) postRequest);
        } else if ("OFF".equals(postRequest.getStatus())) {
            return handleOfflinePost((OfflinePostRequest) postRequest);
        } else {
            throw new IllegalArgumentException("유효하지 않은 상태입니다.");
        }
    }

    // 온라인 포스트 처리 로직
    private Post handleOnlinePost(OnlinePostRequest onlinePostRequest) {

        Post post = Post.builder()
                .gameTitle(onlinePostRequest.getGameTitle())
                .gameGenre(onlinePostRequest.getGameGenre())
                .status(Post.OnOffStatus.ON)
                .userId(onlinePostRequest.getUserId())
                .mateCnt(onlinePostRequest.getMateCnt())
                .mateContent(onlinePostRequest.getMateContent())
                .build();

        return postRepository.save(post);
    }

    //오프라인 포스트 처리 로직
    private Post handleOfflinePost(OfflinePostRequest offlinePostRequest) {


        Post post = Post.builder()
                .gameTitle(offlinePostRequest.getGameTitle())
                .gameGenre(offlinePostRequest.getGameGenre())
                .status(Post.OnOffStatus.OFF)
                .userId(offlinePostRequest.getUserId())
                .mateCnt(offlinePostRequest.getMateCnt())
                .mateContent(offlinePostRequest.getMateContent())
                .mateRegionSi(offlinePostRequest.getMateRegionSi())
                .mateRegionGu(offlinePostRequest.getMateRegionGu())
                .latitude(offlinePostRequest.getLatitude())
                .longitude(offlinePostRequest.getLongitude())
                .build();

        return postRepository.save(post);
    }



}