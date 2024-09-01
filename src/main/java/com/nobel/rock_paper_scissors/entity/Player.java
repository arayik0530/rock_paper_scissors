package com.nobel.rock_paper_scissors.entity;

import com.nobel.rock_paper_scissors.model.PlayerRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PLAYER")
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
    private List<Game> games = new ArrayList<>();

    @ElementCollection(targetClass = PlayerRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "PLAYER_ROLE", joinColumns = @JoinColumn(name = "PLAYER_ID",
            foreignKey = @ForeignKey(name = "PLAYER_ID_FK",
                    foreignKeyDefinition = """
                            foreign key (PLAYER_ID) references PLAYER
                            on delete cascade
                            """
            )))
    @Enumerated(EnumType.STRING)
    private Set<PlayerRole> roles = new HashSet<>();
}
