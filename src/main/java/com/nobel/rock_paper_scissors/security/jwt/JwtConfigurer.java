package com.nobel.rock_paper_scissors.security.jwt;

import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final PlayerRepository playerRepository;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, PlayerRepository playerRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.playerRepository = playerRepository;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider, playerRepository);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
