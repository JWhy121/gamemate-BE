package com.example.gamemate.domain.game.service;

import com.example.gamemate.domain.game.dto.GameCommentDto;
import com.example.gamemate.global.exception.GameExceptionCode;
import com.example.gamemate.global.exception.RestApiException;
import com.example.gamemate.domain.game.mapper.GameCommentMapper;
import com.example.gamemate.domain.game.entity.Game;
import com.example.gamemate.domain.game.entity.GameComment;
import com.example.gamemate.domain.game.repository.GameCommentRepository;
import com.example.gamemate.domain.game.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameCommentService {

    private final GameCommentRepository gameCommentRepository;
    private final GameCommentMapper gameCommentMapper;
    private final GameRepository gameRepository;

    public GameCommentService(GameCommentRepository gameCommentRepository, GameCommentMapper gameCommentMapper, GameRepository gameRepository) {
        this.gameCommentRepository = gameCommentRepository;
        this.gameCommentMapper = gameCommentMapper;
        this.gameRepository = gameRepository;
    }

    public Page<GameCommentDto> getAllComments(Long gameId, Pageable pageable) {
        Page<GameComment> comments = gameCommentRepository.findByGameIdAndDeletedDateIsNull(gameId, pageable);
        return comments.map(gameCommentMapper::toDto);
    }

    public GameCommentDto getCommentById(Long gameId, Long commentId) {
        GameComment comment = gameCommentRepository.findByGameIdAndIdAndDeletedDateIsNull(gameId, commentId)
                .orElseThrow(() -> new RestApiException(GameExceptionCode.GAME_COMMENT_NOT_FOUND));
        return gameCommentMapper.toDto(comment);
    }

    public GameCommentDto createComment(Long gameId, GameCommentDto commentDto) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RestApiException(GameExceptionCode.GAME_NOT_FOUND));

        GameComment comment = gameCommentMapper.toEntity(commentDto);
        comment.setGame(game);
        GameComment savedComment = gameCommentRepository.save(comment);
        return gameCommentMapper.toDto(savedComment);
    }

    public GameCommentDto updateComment(Long gameId, Long commentId, GameCommentDto commentDto) {
        GameComment comment = gameCommentRepository.findByGameIdAndIdAndDeletedDateIsNull(gameId, commentId)
                .orElseThrow(() -> new RestApiException(GameExceptionCode.GAME_COMMENT_NOT_FOUND));
        gameCommentMapper.updateEntityFromDto(commentDto, comment);
        GameComment updatedComment = gameCommentRepository.save(comment);
        return gameCommentMapper.toDto(updatedComment);
    }

    public void deleteComment(Long gameId, Long commentId) {
        GameComment comment = gameCommentRepository.findByGameIdAndIdAndDeletedDateIsNull(gameId, commentId)
                .orElseThrow(() -> new RestApiException(GameExceptionCode.GAME_COMMENT_NOT_FOUND));
        comment.setDeletedDate(LocalDateTime.now()); // Soft delete 처리
        gameCommentRepository.save(comment);
    }
}
