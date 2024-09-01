package com.nobel.rock_paper_scissors.service.impl;

import com.nobel.rock_paper_scissors.entity.Game;
import com.nobel.rock_paper_scissors.entity.Player;
import com.nobel.rock_paper_scissors.model.GameResult;
import com.nobel.rock_paper_scissors.model.Move;
import com.nobel.rock_paper_scissors.model.Outcome;
import com.nobel.rock_paper_scissors.model.PlayResponse;
import com.nobel.rock_paper_scissors.repository.GameRepository;
import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import com.nobel.rock_paper_scissors.service.AuthenticationService;
import com.nobel.rock_paper_scissors.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
            if(game.getPlayer().getId().equals(authenticationService.getMe())) {
                game.setIsFinished(Boolean.TRUE);
                gameRepository.save(game);
                return new GameResult(game.getPlayerScore(), game.getComputerScore());
            }
        }
        throw new RuntimeException("Game not found");
    }

    @Override
    public PlayResponse playGame(Move playerMove) {
        Move computerMove = generateComputerMove(playerMove);
        Outcome outcome = playerMove.compareMoves(computerMove);
        saveStatistics(outcome);

        return new PlayResponse(playerMove, computerMove, outcome);
    }


    private Move generateComputerMove(Move playerMove) {
        List<Move> moves = List.of(Move.ROCK, Move.PAPER, Move.SCISSORS);
        int playerMoveIndex = moves.indexOf(playerMove);
        int biasedIndex = (playerMoveIndex + 1 + random.nextInt(moves.size())) % moves.size();
        return moves.get(biasedIndex);
    }


    private void saveStatistics(Outcome outcome) {
//        GameStatistics statistics = statisticsRepository.findById(1L).orElse(new GameStatistics(1, 0, 0, 0));
////        statistics = switch (outcome) {
////            case WIN -> new GameStatistics(statistics.getId(), statistics.getTotalGames() + 1, statistics.getPlayerWins() + 1, statistics.getComputerWins(), statistics.getDraws());
////            case LOSE -> new GameStatistics(statistics.getId(), statistics.getTotalGames() + 1, statistics.getPlayerWins(), statistics.getComputerWins() + 1, statistics.getDraws());
////            case DRAW -> new GameStatistics(statistics.getId(), statistics.getTotalGames() + 1, statistics.getPlayerWins(), statistics.getComputerWins(), statistics.getDraws() + 1);
////        };
//
//        statisticsRepository.save(statistics);
    }
}
