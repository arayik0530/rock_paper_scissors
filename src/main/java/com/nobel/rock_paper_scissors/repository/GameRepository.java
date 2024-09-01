package com.nobel.rock_paper_scissors.repository;

import com.nobel.rock_paper_scissors.entity.Game;
import com.nobel.rock_paper_scissors.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT new com.nobel.rock_paper_scissors.model.Statistics(" +
            "SUM(CASE WHEN g.playerScore > g.computerScore THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN g.playerScore < g.computerScore THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN g.playerScore = g.computerScore THEN 1 ELSE 0 END)) " +
            "FROM Game g WHERE g.player.id = :loggedInUserId AND g.isFinished = TRUE")
    Statistics getStatistics(@Param("loggedInUserId") Long loggedInUserId);
}
