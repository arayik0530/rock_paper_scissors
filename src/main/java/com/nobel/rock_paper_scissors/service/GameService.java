package com.nobel.rock_paper_scissors.service;

import com.nobel.rock_paper_scissors.model.GameResult;
import com.nobel.rock_paper_scissors.model.Move;
import com.nobel.rock_paper_scissors.model.PlayRequest;
import com.nobel.rock_paper_scissors.model.PlayResponse;

public interface GameService {
    PlayResponse playGame(PlayRequest request);

    Long startGame();

    GameResult finishGame(Long gameId);
}
