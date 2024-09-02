package com.nobel.rock_paper_scissors;

import com.nobel.rock_paper_scissors.model.*;
import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RockPaperScissorsIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCompleteGameFlow() {
        final String name = "name";
        final String testUser = "testUser" + System.currentTimeMillis();
        final String password = "password";
        final String testPassword = "testPassword";

        // Step 1: Register a new user
        Map<String, String> registrationRequest = new HashMap<>();
        registrationRequest.put(name, testUser);
        registrationRequest.put(password, testPassword);

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity("/api/auth/signup", registrationRequest, Void.class);
        assertEquals(HttpStatus.OK, registerResponse.getStatusCode());

        // Step 2: Login and retrieve the bearer token
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put(name, testUser);
        loginRequest.put(password, testPassword);

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, LoginResponse.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        // Extract bearer token from login response
        String token = loginResponse.getBody().token();
        assertNotNull(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // Step 3: Start a new game
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Long> startGameResponse = restTemplate.exchange("/api/game/start", HttpMethod.GET, requestEntity, Long.class);
        assertEquals(HttpStatus.OK, startGameResponse.getStatusCode());
        Long gameId = startGameResponse.getBody();
        assertNotNull(gameId);

        // Step 4: Make a move
        PlayRequest playRequest = new PlayRequest(Move.ROCK, null, gameId);
        HttpEntity<PlayRequest> playRequestEntity = new HttpEntity<>(playRequest, headers);
        ResponseEntity<PlayResponse> playResponse = restTemplate.exchange("/api/game/play", HttpMethod.POST, playRequestEntity, PlayResponse.class);

        assertEquals(HttpStatus.OK, playResponse.getStatusCode());
        PlayResponse result = playResponse.getBody();
        assertNotNull(result);
        assertNotNull(result.outcome());

        // Step 5: Finish the game
        ResponseEntity<GameResult> finishGameResponse = restTemplate.exchange("/api/game/" + gameId + "finish", HttpMethod.GET, requestEntity, GameResult.class);

        assertEquals(HttpStatus.OK, finishGameResponse.getStatusCode());
        GameResult gameResult = finishGameResponse.getBody();
        assertNotNull(gameResult);
        assertNotNull(gameResult.playerScore());
        assertNotNull(gameResult.computerScore());

    }
}
