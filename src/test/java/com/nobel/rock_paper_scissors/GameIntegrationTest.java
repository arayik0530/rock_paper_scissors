package com.nobel.rock_paper_scissors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPlayRock() throws Exception {
        mockMvc.perform(post("/game/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerMove\":\"ROCK\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerMove").value("ROCK"))
                .andExpect(jsonPath("$.computerMove").exists())
                .andExpect(jsonPath("$.outcome").exists());
    }

    @Test
    public void testPlayInvalidMove() throws Exception {
        mockMvc.perform(post("/game/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerMove\":\"INVALID\"}"))
                .andExpect(status().isBadRequest());
    }
}
