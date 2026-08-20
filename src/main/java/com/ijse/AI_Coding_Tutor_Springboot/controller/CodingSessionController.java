package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.ResponseCodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.service.CodingSessionService;
import com.ijse.AI_Coding_Tutor_Springboot.service.GeminiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
public class CodingSessionController {

    private final CodingSessionService codingSessionService;

    public CodingSessionController(CodingSessionService codingSessionService) {
        this.codingSessionService = codingSessionService;
    }

    @PostMapping("/createSession")
    public CommonResponse createNewCodingSession(@RequestBody CodingSessionDTO codingSessionDTO){
        ResponseCodingSessionDTO responseCodingSessionDTO= codingSessionService.createNewCodingSession(codingSessionDTO);
        return new CommonResponse(OPERATION_SUCCESS,responseCodingSessionDTO,SUCCESS_MESSAGE);
    }
}
