package com.nobel.rock_paper_scissors.controller;

import com.nobel.rock_paper_scissors.entity.Player;
import com.nobel.rock_paper_scissors.model.LoginResponse;
import com.nobel.rock_paper_scissors.model.PlayerAuthDTO;
import com.nobel.rock_paper_scissors.security.jwt.JwtService;
import com.nobel.rock_paper_scissors.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Player> register(@RequestBody PlayerAuthDTO playerAuthDTO) {
        authenticationService.signup(playerAuthDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody PlayerAuthDTO playerAuthDTO) {
        Player authenticatedPlayer = authenticationService.authenticate(playerAuthDTO);
        String jwtToken = jwtService.generateToken(authenticatedPlayer);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
