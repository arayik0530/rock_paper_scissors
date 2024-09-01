package com.nobel.rock_paper_scissors.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GAME")
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_SCORE", nullable = false)
    private Integer userScore = 0;

    @Column(name = "COMPUTER_SCORE", nullable = false)
    private Integer computerScore = 0;

    @Column(name = "IS_FINISHED", nullable = false)
    private Boolean isFinished = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    private Player player;
}
