package com.nobel.rock_paper_scissors.service;

import com.nobel.rock_paper_scissors.model.PlayerAuthDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface PlayerService {
    void register(PlayerAuthDTO playerAuthDTO);

    String login(PlayerAuthDTO playerAuthDTO);

    Long getMe();
}
