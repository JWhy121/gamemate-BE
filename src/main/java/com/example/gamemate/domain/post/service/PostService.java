package com.example.gamemate.domain.post.service;

import com.example.gamemate.domain.post.entity.Post;
import com.example.gamemate.domain.post.dto.*;
import com.example.gamemate.domain.post.entity.PostComment;
import com.example.gamemate.domain.post.mapper.PostMapper;
import com.example.gamemate.domain.post.repository.PostRepository;
import com.example.gamemate.global.exception.PostExceptionCode;
import com.example.gamemate.global.exception.RestApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper mapper;



    public PostService(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    //온라인 게시글 리스트 조회
    public Page<PostResponseDTO> readPostsOnline(Pageable pageable){
        Page<Post> postPage = postRepository.findByStatus(Post.OnOffStatus.ON, pageable);

        return postPage.map(PostResponseDTO::new);
    }

    //오프라인 게시글 리스트 조회
    public Page<PostResponseDTO> readPostsOffline(Pageable pageable){
        Page<Post> postPage = postRepository.findByStatus(Post.OnOffStatus.OFF, pageable);

        return postPage.map(PostResponseDTO::new);
    }

    //게시글 조회
    public PostResponseDTO readPost(Long id){

        // 게시글 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RestApiException(PostExceptionCode.POST_NOT_FOUND));

        //부모 댓글 필터링해서 가져오기
        List<PostComment> parentComments = post.getPostComments().stream()
                .filter(postComment -> postComment.getParentComment() == null)
                .collect(Collectors.toList());

        System.out.println(parentComments);

        //댓글 응답 DTO로 변환
        List<PostCommentsResponseDTO> postCommentDto = parentComments.stream()
                .map(postComment -> PostCommentsResponseDTO.builder()
                        .id(postComment.getId())
                        .nickname(postComment.getNickname())
                        .content(postComment.getContent())
                        .recomments(getRecomments(postComment))
                        .build())
                .collect(Collectors.toList());

        System.out.println(postCommentDto);

        return PostResponseDTO.builder()
                .userId(post.getUserId())
                .gameTitle(post.getGameTitle())
                .status(post.getStatus().toString())
                .gameGenre(post.getGameGenre())
                .mateCnt(post.getMateCnt())
                .mateContent(post.getMateContent())
                .mateRegionSi(post.getMateRegionSi())
                .mateRegionGu(post.getMateRegionGu())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .postComments(postCommentDto)
                .build();
    }

    //게시글 댓글 조회
    private List<RecommentsResponseDTO> getRecomments(PostComment parentComment){
        return parentComment.getReComments().stream()
                .map(recomment -> RecommentsResponseDTO.builder()
                        .id(recomment.getId())
                        .nickname(recomment.getNickname())
                        .content(recomment.getContent())
                        .build())
                .collect(Collectors.toList());
    }


    //게시글 생성
    //Status 값에 따라서 다른 로직 처리
    public Post createPost(PostDTO postDTO) {

        if ("ON".equals(postDTO.getStatus())) {
            return handleOnlinePost((OnlinePostDTO) postDTO);
        } else if ("OFF".equals(postDTO.getStatus())) {
            return handleOfflinePost((OfflinePostDTO) postDTO);
        } else {
            throw new IllegalArgumentException("유효하지 않은 상태입니다.");
        }
    }

    //게시글 수정
    public PostResponseDTO updatePost(Long id, PostUpdateDTO postUpdateDTO){

        if (postUpdateDTO.getStatus().equals("ON")){

            return postRepository.findById(id)
                    .map(existingPost -> {
                        existingPost.updateOnlinePost(postUpdateDTO.getMateCnt(), postUpdateDTO.getMateContent());

                        Post updatedPost = postRepository.save(existingPost);
                        return mapper.PostToOfflinePostResponse(updatedPost);
                    })
                    .orElseThrow(() -> new IllegalStateException("Post with id " + id + "does note exist"));
        } else if (postUpdateDTO.getStatus().equals("OFF")){
            return postRepository.findById(id)
                    .map(existingPost -> {
                        existingPost.updateOfflinePost(postUpdateDTO.getMateCnt(), postUpdateDTO.getMateContent(),
                                postUpdateDTO.getMateRegionSi(), postUpdateDTO.getMateResionGu(),
                                postUpdateDTO.getLatitude(), postUpdateDTO.getLongitude());

                        Post updatedPost = postRepository.save(existingPost);
                        return mapper.PostToOfflinePostResponse(updatedPost);
                    })
                    .orElseThrow(() -> new IllegalStateException("Post with id " + id + "does note exist"));
        }
        else throw new IllegalArgumentException("잘못된 접근");
    }

    //게시글 삭제
    public void deletePost(Long id){
        postRepository.hardDeleteComments(id);
        postRepository.deleteById(id);
    }


    // 온라인 포스트 처리 로직
    private Post handleOnlinePost(OnlinePostDTO onlinePostRequest) {

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
    private Post handleOfflinePost(OfflinePostDTO offlinePostDTO) {


        Post post = Post.builder()
                .gameTitle(offlinePostDTO.getGameTitle())
                .gameGenre(offlinePostDTO.getGameGenre())
                .status(Post.OnOffStatus.OFF)
                .userId(offlinePostDTO.getUserId())
                .mateCnt(offlinePostDTO.getMateCnt())
                .mateContent(offlinePostDTO.getMateContent())
                .mateRegionSi(offlinePostDTO.getMateRegionSi())
                .mateRegionGu(offlinePostDTO.getMateRegionGu())
                .latitude(offlinePostDTO.getLatitude())
                .longitude(offlinePostDTO.getLongitude())
                .build();

        return postRepository.save(post);
    }



}