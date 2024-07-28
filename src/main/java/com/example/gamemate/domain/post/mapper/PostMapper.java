package com.example.gamemate.domain.post.mapper;


import com.example.gamemate.domain.post.Post;
import com.example.gamemate.domain.post.dto.OfflinePostRequest;
import com.example.gamemate.domain.post.dto.OfflinePostResponse;
import com.example.gamemate.domain.post.dto.OnlinePostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    Post OfflinePostRequestToPost(OfflinePostRequest offlinePostRequest);

    Post OnlinePostRequestToPost(OnlinePostRequest onlinePostRequest);

    OfflinePostResponse PostToOfflinePostResponse(Post post);
}