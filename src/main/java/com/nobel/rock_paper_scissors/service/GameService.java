package com.nobel.rock_paper_scissors.service;

import com.nobel.rock_paper_scissors.model.*;

public interface GameService {
    PlayResponse playGame(PlayRequest request);

    Long startGame();

    GameResult finishGame(Long gameId);

    Statistics generateStatistics();
}
