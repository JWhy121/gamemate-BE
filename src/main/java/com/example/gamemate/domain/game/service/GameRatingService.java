package com.example.gamemate.domain.game.service;

import com.example.gamemate.domain.game.dto.GameRatingDto;
import com.example.gamemate.domain.user.entity.User;
import com.example.gamemate.domain.user.repository.UserRepository;
import com.example.gamemate.global.exception.GameExceptionCode;
import com.example.gamemate.global.exception.RestApiException;
import com.example.gamemate.domain.game.mapper.GameRatingMapper;
import com.example.gamemate.domain.game.entity.Game;
import com.example.gamemate.domain.game.entity.GameRating;
import com.example.gamemate.domain.game.repository.GameRatingRepository;
import com.example.gamemate.domain.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameRatingService {

    private final GameRatingRepository gameRatingRepository;
    private final GameRatingMapper gameRatingMapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameRatingService(GameRatingRepository gameRatingRepository, GameRatingMapper gameRatingMapper, GameRepository gameRepository, UserRepository userRepository) {
        this.gameRatingRepository = gameRatingRepository;
        this.gameRatingMapper = gameRatingMapper;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public GameRatingDto getUserRatingForGame(String username, Long gameId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RestApiException(GameExceptionCode.USER_NOT_FOUND);
        }

        // 평점이 존재하지 않을 경우 기본 평점 0을 반환하도록 수정
        GameRating rating = gameRatingRepository.findByUserIdAndGameId(user.getId(), gameId)
                .orElseGet(() -> GameRating.builder()
                        .user(user)
                        .game(Game.builder().id(gameId).build()) // 게임 정보에 gameId만 설정
                        .rating(0) // 기본 평점 0
                        .build()
                );

        return gameRatingMapper.toDto(rating);
    }


    @Transactional
    public GameRatingDto createUserRatingForGame(String username, Long gameId, GameRatingDto ratingDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RestApiException(GameExceptionCode.USER_NOT_FOUND);
        }

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RestApiException(GameExceptionCode.GAME_NOT_FOUND));

        GameRating rating = gameRatingMapper.toEntity(ratingDto);
        rating.setUser(user);
        rating.setGame(game);

        GameRating savedRating = gameRatingRepository.save(rating);
        return gameRatingMapper.toDto(savedRating);
    }

    @Transactional
    public GameRatingDto updateUserRatingForGame(String username, Long gameId, GameRatingDto ratingDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RestApiException(GameExceptionCode.USER_NOT_FOUND);
        }

        GameRating rating = gameRatingRepository.findByUserIdAndGameId(user.getId(), gameId)
                .orElseThrow(() -> new RestApiException(GameExceptionCode.GAME_RATING_NOT_FOUND));

        // 기존의 식별자를 유지하며 DTO로부터 필요한 정보를 업데이트
        if (ratingDto.getRating() != 0) {
            rating.setRating(ratingDto.getRating());
        }

        GameRating updatedRating = gameRatingRepository.save(rating);
        return gameRatingMapper.toDto(updatedRating);
    }

    @Transactional
    public void deleteUserRatingForGame(String username, Long gameId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RestApiException(GameExceptionCode.USER_NOT_FOUND);
        }

        GameRating rating = gameRatingRepository.findByUserIdAndGameId(user.getId(), gameId)
                .orElseThrow(() -> new RestApiException(GameExceptionCode.GAME_RATING_NOT_FOUND));

        gameRatingRepository.delete(rating);
    }

    @Transactional
    public void deleteUserRatingsByUsername(String username) {
        // 사용자 조회
        User user = userRepository.findByUsername(username);
        log.info(username);

        if (user == null) {
            throw new RestApiException(GameExceptionCode.USER_NOT_FOUND);
        }

        // 해당 사용자의 모든 게임 평점 조회
        List<GameRating> ratings = gameRatingRepository.findByUserId(user.getId());
        log.info("findByUserId");

        // 모든 평점 삭제
        gameRatingRepository.deleteAll(ratings);
        log.info("deleteAll");
    }
}