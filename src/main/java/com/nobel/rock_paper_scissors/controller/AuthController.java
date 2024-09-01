package com.nobel.rock_paper_scissors.controller;

import com.nobel.rock_paper_scissors.model.PlayerAuthDTO;
import com.nobel.rock_paper_scissors.security.jwt.JwtTokenProvider;
import com.nobel.rock_paper_scissors.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private PlayerService playerService;

    public AuthController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("register")
    public void register(@RequestBody PlayerAuthDTO registration) {
        playerService.register(registration);
    }

    @PostMapping("login")
    public String login(@RequestBody PlayerAuthDTO playerAuthDTO) {
        return playerService.login(playerAuthDTO);
    }

}
