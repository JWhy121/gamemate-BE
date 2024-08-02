package com.example.gamemate.domain.game.service;

import com.example.gamemate.domain.game.dto.GameApiResponse;
import com.example.gamemate.domain.game.dto.GameDto;
import com.example.gamemate.domain.game.entity.Game;
import com.example.gamemate.domain.game.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameApiClient gameApiClient;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void fetchAndSaveGames(String gametitle, String entname, String rateno, String startdate, String enddate, int display, int pageno) {
        // 외부 API를 호출하여 게임 정보를 가져옴
        String jsonResponse = gameApiClient.fetchGames(gametitle, entname, rateno, startdate, enddate, display, pageno);

        // 응답 데이터 디버깅
        logger.debug("Fetched response: {}", jsonResponse);

        if (jsonResponse != null) {
            try {
                // JSON 응답을 GameApiResponse 객체로 변환
                GameApiResponse response = objectMapper.readValue(jsonResponse, GameApiResponse.class);

                // 응답에서 게임 항목을 추출하여 데이터베이스에 저장
                if (response != null && response.getResult() != null && response.getResult().getItems() != null) {
                    for (GameApiResponse.GameItem item : response.getResult().getItems()) {
                        Game game = Game.builder()
                                .title(item.getGametitle())
                                .developer(item.getEntname())
                                .description(item.getSummary())
                                .classes(item.getGivenrate())
                                .genre(item.getGenre())
                                .platform(item.getPlatform())
                                .build();

                        // 데이터베이스에 저장
                        gameRepository.save(game);
                        logger.debug("Game saved: {}", game);
                    }
                } else {
                    logger.warn("No games found in the response");
                }
            } catch (IOException e) {
                logger.error("Failed to parse JSON response", e);
            }
        }
    }

    public List<GameDto> getAllGames() {
        return gameRepository.findAll().stream().map(game -> new GameDto(
                game.getId(), game.getTitle(), game.getDeveloper(), game.getDescription(), game.getClasses(), game.getGenre(), game.getPlatform()
        )).collect(Collectors.toList());
    }

    public GameDto getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        return new GameDto(game.getId(), game.getTitle(), game.getDeveloper(), game.getDescription(), game.getClasses(), game.getGenre(), game.getPlatform());
    }

    public GameDto createGame(GameDto gameDto) {
        Game game = new Game();
        game.setTitle(gameDto.getTitle());
        game.setDeveloper(gameDto.getDeveloper());
        game.setDescription(gameDto.getDescription());
        game.setClasses(gameDto.getClasses());
        game.setGenre(gameDto.getGenre());
        game.setPlatform(gameDto.getPlatform());
        Game savedGame = gameRepository.save(game);
        return new GameDto(savedGame.getId(), savedGame.getTitle(), savedGame.getDeveloper(), savedGame.getDescription(), savedGame.getClasses(), savedGame.getGenre(), savedGame.getPlatform());
    }

    public GameDto updateGame(Long id, GameDto gameDto) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        game.setTitle(gameDto.getTitle());
        game.setDeveloper(gameDto.getDeveloper());
        game.setDescription(gameDto.getDescription());
        game.setClasses(gameDto.getClasses());
        game.setGenre(gameDto.getGenre());
        game.setPlatform(gameDto.getPlatform());
        Game updatedGame = gameRepository.save(game);
        return new GameDto(updatedGame.getId(), updatedGame.getTitle(), updatedGame.getDeveloper(), updatedGame.getDescription(), updatedGame.getClasses(), updatedGame.getGenre(), updatedGame.getPlatform());
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
