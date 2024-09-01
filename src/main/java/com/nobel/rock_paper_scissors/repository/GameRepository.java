package com.nobel.rock_paper_scissors.repository;

import com.nobel.rock_paper_scissors.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
