package com.nobel.rock_paper_scissors.service.impl;

import com.nobel.rock_paper_scissors.entity.Player;
import com.nobel.rock_paper_scissors.model.PlayerAuthDTO;
import com.nobel.rock_paper_scissors.model.PlayerRole;
import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import com.nobel.rock_paper_scissors.security.jwt.JwtTokenProvider;
import com.nobel.rock_paper_scissors.security.jwt.JwtUser;
import com.nobel.rock_paper_scissors.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;



    @Override
    public void register(PlayerAuthDTO registration) {
        Optional<Player> byName = playerRepository.findByName(registration.name());
        if (byName.isPresent()) {
//            throw new UserAlreadyExistsException(registration.name());
            throw new RuntimeException(registration.name());
        }

        Player player = new Player();
        player.setName(registration.name());
        player.setPassword(passwordEncoder.encode(registration.password()));
        player.getRoles().add(PlayerRole.PLAYER);
        playerRepository.save(player);
    }

    @Override
    public String login(PlayerAuthDTO playerAuthDTO) {
        String name = playerAuthDTO.name();
        Player player = playerRepository.findByName(name)
//                .orElseThrow(() -> new UserNotFoundException("User " + name + "does not exist."));
                .orElseThrow(() -> new RuntimeException("User " + name + "does not exist."));

        JwtUser jwtUser = (JwtUser) authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                name, playerAuthDTO.password()))
                .getPrincipal();

        return jwtTokenProvider.createToken(jwtUser.getId(), jwtUser.getUsername(),
                player.getRoles()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toList()));

    }

    @Override
    public Long getMe() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}

