package com.nobel.rock_paper_scissors.security;

import com.nobel.rock_paper_scissors.entity.Player;
import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import com.nobel.rock_paper_scissors.security.jwt.JwtUser;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class JwtUserDetailsService implements UserDetailsService {
    private final PlayerRepository playerRepository;

    public JwtUserDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        Optional<Player> optionalUserEntity = playerRepository.findByName(name);

        if (optionalUserEntity.isPresent()) {
            Player player = optionalUserEntity.get();
            return new JwtUser(player.getId(), player.getName(), player.getPassword(), player.getRoles());
        }
        throw new UsernameNotFoundException(name);
    }
}
