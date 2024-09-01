package com.nobel.rock_paper_scissors.controller;


import com.nobel.rock_paper_scissors.model.GameResult;
import com.nobel.rock_paper_scissors.model.PlayRequest;
import com.nobel.rock_paper_scissors.model.PlayResponse;
import com.nobel.rock_paper_scissors.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/start")
    public Long startGame() {
        return gameService.startGame();
    }

    @GetMapping("/{gameId}finish")
    public GameResult finishGame(@PathVariable Long gameId) {
        return gameService.finishGame(gameId);
    }

    @PostMapping("/play")
    public PlayResponse play(@RequestBody PlayRequest request) {
        return gameService.playGame(request);
    }

    @GetMapping("/get-statistics")
    public String getStatistics() {

        System.out.println(111);

        return "asd";
    }
}
