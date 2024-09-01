package com.nobel.rock_paper_scissors.service.impl;

import com.nobel.rock_paper_scissors.entity.Player;
import com.nobel.rock_paper_scissors.model.PlayerAuthDTO;
import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import com.nobel.rock_paper_scissors.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PlayerRepository playerRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public Player signup(PlayerAuthDTO playerAuthDTO) {
        Optional<Player> byName = playerRepository.findByName(playerAuthDTO.name());
        if (byName.isPresent()) {
            throw new RuntimeException("Player already exists.");
        }

        Player player = new Player();
        player.setName(playerAuthDTO.name());
        player.setPassword(passwordEncoder.encode(playerAuthDTO.password()));

        return playerRepository.save(player);
    }

    @Override
    public Player authenticate(PlayerAuthDTO playerAuthDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        playerAuthDTO.name(),
                        playerAuthDTO.password()
                )
        );

        return playerRepository.findByName(playerAuthDTO.name())
                .orElseThrow();
    }

    @Override
    public Long getMe() {
        Player player = (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return player.getId();
    }
}
