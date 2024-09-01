package com.nobel.rock_paper_scissors.service;

import com.nobel.rock_paper_scissors.entity.Player;
import com.nobel.rock_paper_scissors.model.PlayerAuthDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    Player signup(PlayerAuthDTO playerAuthDTO);

    Player authenticate(PlayerAuthDTO playerAuthDTO);

    Long getMe();
}
