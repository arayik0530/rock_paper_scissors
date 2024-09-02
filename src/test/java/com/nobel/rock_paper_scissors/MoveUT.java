package com.nobel.rock_paper_scissors;

import com.nobel.rock_paper_scissors.model.Move;
import com.nobel.rock_paper_scissors.model.Outcome;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveUT {

    @Test
    void testRockBeatsScissors() {
        assertEquals(Outcome.WIN, Move.ROCK.compareMoves(Move.SCISSORS));
    }

    @Test
    void testRockLosesToPaper() {
        assertEquals(Outcome.LOSE, Move.ROCK.compareMoves(Move.PAPER));
    }

    @Test
    void testRockDrawsWithRock() {
        assertEquals(Outcome.DRAW, Move.ROCK.compareMoves(Move.ROCK));
    }

    @Test
    void testPaperBeatsRock() {
        assertEquals(Outcome.WIN, Move.PAPER.compareMoves(Move.ROCK));
    }

    @Test
    void testScissorsLoseToRock() {
        assertEquals(Outcome.LOSE, Move.SCISSORS.compareMoves(Move.ROCK));
    }

    @Test
    void testScissorsBeatPaper() {
        assertEquals(Outcome.WIN, Move.SCISSORS.compareMoves(Move.PAPER));
    }

    @Test
    void testPaperDrawsWithPaper() {
        assertEquals(Outcome.DRAW, Move.PAPER.compareMoves(Move.PAPER));
    }
}

