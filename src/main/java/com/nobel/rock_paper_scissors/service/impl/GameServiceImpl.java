package com.nobel.rock_paper_scissors.service.impl;

import com.nobel.rock_paper_scissors.model.Move;
import com.nobel.rock_paper_scissors.model.Outcome;
import com.nobel.rock_paper_scissors.model.PlayResponse;
import com.nobel.rock_paper_scissors.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {

    private final Random random;
//    private final GameStatisticsRepository statisticsRepository;

    public GameServiceImpl() {
        this.random = new Random();
//        this.statisticsRepository = statisticsRepository;
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
