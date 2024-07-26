package com.example.gamemate.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/*
 *
 * 게시글에서 발생할 수 있는 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post does not exist"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}