package com.example.gamemate.domain.game.service;

import com.example.gamemate.domain.game.dto.GameRatingDto;
import com.example.gamemate.domain.game.mapper.GameRatingMapper;
import com.example.gamemate.domain.game.entity.Game;
import com.example.gamemate.domain.game.entity.GameRating;
import com.example.gamemate.domain.game.repository.GameRatingRepository;
import com.example.gamemate.domain.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameRatingService {

    @Autowired
    private GameRatingRepository gameRatingRepository;

    @Autowired
    private GameRatingMapper gameRatingMapper;

    @Autowired
    private GameRepository gameRepository;

    public List<GameRatingDto> getAllRatings(Long gameId) {
        List<GameRating> ratings = gameRatingRepository.findByGameId(gameId);
        return ratings.stream().map(gameRatingMapper::toDto).collect(Collectors.toList());
    }

    public GameRatingDto getRatingById(Long gameId, Long ratingId) {
        GameRating rating = gameRatingRepository.findByGameIdAndId(gameId, ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        return gameRatingMapper.toDto(rating);
    }

    public GameRatingDto createRating(Long gameId, GameRatingDto ratingDto) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        GameRating rating = gameRatingMapper.toEntity(ratingDto);
        rating.setGame(game); // gameId 대신 game 객체 설정
        GameRating savedRating = gameRatingRepository.save(rating);
        return gameRatingMapper.toDto(savedRating);
    }

    public GameRatingDto updateRating(Long gameId, Long ratingId, GameRatingDto ratingDto) {
        GameRating rating = gameRatingRepository.findByGameIdAndId(gameId, ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        gameRatingMapper.updateEntityFromDto(ratingDto, rating);
        GameRating updatedRating = gameRatingRepository.save(rating);
        return gameRatingMapper.toDto(updatedRating);
    }

    public void deleteRating(Long gameId, Long ratingId) {
        gameRatingRepository.deleteByGameIdAndId(gameId, ratingId);
    }
}
