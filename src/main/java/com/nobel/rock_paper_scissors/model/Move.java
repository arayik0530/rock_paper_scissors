package com.nobel.rock_paper_scissors.model;

public enum Move {
    ROCK, PAPER, SCISSORS;

    public Outcome compareMoves(Move otherMove) {
        if (this == otherMove) {
            return Outcome.DRAW;
        }

        return switch (this) {
            case ROCK -> (otherMove == SCISSORS) ? Outcome.WIN : Outcome.LOSE;
            case PAPER -> (otherMove == ROCK) ? Outcome.WIN : Outcome.LOSE;
            case SCISSORS -> (otherMove == PAPER) ? Outcome.WIN : Outcome.LOSE;
        };
    }
}
