package com.nobel.rock_paper_scissors.service.impl;

import com.nobel.rock_paper_scissors.entity.Game;
import com.nobel.rock_paper_scissors.entity.Player;
import com.nobel.rock_paper_scissors.model.*;
import com.nobel.rock_paper_scissors.repository.GameRepository;
import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import com.nobel.rock_paper_scissors.service.AuthenticationService;
import com.nobel.rock_paper_scissors.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.nobel.rock_paper_scissors.model.Move.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final Random random = new Random();
    private final AuthenticationService authenticationService;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Override
    public Long startGame() {
        Game game = new Game();
        Player player = playerRepository.findById(authenticationService.getMe()).get();
        game.setPlayer(player);
        return gameRepository.save(game).getId();
    }

    @Override
    public GameResult finishGame(Long gameId) {
        Optional<Game> byId = gameRepository.findById(gameId);
        if (byId.isPresent()) {
            Game game = byId.get();
            if (game.getPlayer().getId().equals(authenticationService.getMe())) {
                game.setIsFinished(Boolean.TRUE);
                gameRepository.save(game);
                return new GameResult(game.getPlayerScore(), game.getComputerScore());
            }
        }
        throw new RuntimeException("Game not found");
    }

    @Override
    public PlayResponse playGame(PlayRequest request) {
        Game game = gameRepository.findById(request.gameId()).get();

        if (!game.getIsFinished() && game.getPlayer().getId().equals(authenticationService.getMe())) {
            Move computerMove = generateComputerMove(request);
            Outcome outcome = request.playerMove().compareMoves(computerMove);

            Boolean scoreChanged = Boolean.FALSE;
            if (outcome == Outcome.WIN) {
                scoreChanged = Boolean.TRUE;
                game.setPlayerScore(game.getPlayerScore() + 1);
            } else if (outcome == Outcome.LOSE) {
                scoreChanged = Boolean.TRUE;
                game.setComputerScore(game.getComputerScore() + 1);
            }
            if (scoreChanged) {
                gameRepository.save(game);
            }

            return new PlayResponse(request.playerMove(), computerMove, outcome);
        }

        throw new RuntimeException("Bad Request");
    }

    /**
     * Different strategies are used to generate computer move:
     * 1. if it's the first move then the computer generates random move
     * 2. Otherwise, 3 strategies are used:
     * I. Counter the player's last move or
     * II. Mimic the player's last move or
     * III. Again, the computer generates random move
     * These 3 strategies are selected randomly
     */
    private Move generateComputerMove(PlayRequest request) {
        if (request.playerLastMove() != null) {
            int strategy = random.nextInt(3);

            switch (strategy) {
                case 0 -> {
                    return switch (request.playerLastMove()) {
                        case ROCK -> PAPER;
                        case PAPER -> SCISSORS;
                        case SCISSORS -> ROCK;
                    };
                }
                case 1 -> {
                    return request.playerLastMove();
                }
                case 2 -> {
                    return getRandomMove();
                }
            }
        }

        return getRandomMove();
    }

    private Move getRandomMove() {
        return Move.values()[random.nextInt(3)];
    }
}
