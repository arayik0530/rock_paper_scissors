package com.nobel.rock_paper_scissors.service;

import com.nobel.rock_paper_scissors.model.Move;
import com.nobel.rock_paper_scissors.model.PlayResponse;

public interface GameService {
    PlayResponse playGame(Move playerMove);
}
