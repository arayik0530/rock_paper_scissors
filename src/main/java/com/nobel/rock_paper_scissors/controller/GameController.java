package com.nobel.rock_paper_scissors.controller;


import com.nobel.rock_paper_scissors.model.PlayRequest;
import com.nobel.rock_paper_scissors.model.PlayResponse;
import com.nobel.rock_paper_scissors.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
//    private final StatisticsService statisticsService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
//        this.statisticsService = statisticsService;
    }

    @PostMapping("/play")
    public PlayResponse play(@RequestBody PlayRequest request) {
        return gameService.playGame(request.playerMove());
    }

    @GetMapping("/statistics")
    public String getStatistics() {

        System.out.println(111);

        return "asd";
    }
}
