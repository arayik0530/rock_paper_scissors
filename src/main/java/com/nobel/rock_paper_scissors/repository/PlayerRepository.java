package com.nobel.rock_paper_scissors.repository;

import com.nobel.rock_paper_scissors.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByName(String name);
}
