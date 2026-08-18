package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.dto.CodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.service.CodingSessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodingSessionController {

    private final CodingSessionService codingSessionService;

    public CodingSessionController(CodingSessionService codingSessionService) {
        this.codingSessionService = codingSessionService;
    }

    @PostMapping("/createSession")
    public void createNewCodingSession(@RequestBody CodingSessionDTO codingSessionDTO){

    }
}
