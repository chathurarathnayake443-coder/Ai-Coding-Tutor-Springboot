package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.*;
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
    private final GeminiService geminiService;

    public CodingSessionController(CodingSessionService codingSessionService,  GeminiService geminiService) {
        this.codingSessionService = codingSessionService;
        this.geminiService = geminiService;
    }

    @PostMapping("/createSession")
    public CommonResponse createNewCodingSession(@RequestBody CodingSessionDTO codingSessionDTO){
        ResponseCodingSessionDTO responseCodingSessionDTO= codingSessionService.createNewCodingSession(codingSessionDTO);
        return new CommonResponse(OPERATION_SUCCESS,responseCodingSessionDTO,SUCCESS_MESSAGE);
    }

    @PostMapping("/getSolution")
    public CommonResponse getSolution(@RequestBody RequestSolutionDTO requestSolutionDTO){
        try{
            ResponseSolutionDTO responseSolutionDTO = geminiService.generateActualSolution(requestSolutionDTO);
            return new CommonResponse(OPERATION_SUCCESS,responseSolutionDTO,SUCCESS_MESSAGE);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/endSession")
    public CommonResponse endSession(@RequestBody EndSessionDTO endSessionDTO){
        codingSessionService.endSession(endSessionDTO);
        return new CommonResponse(OPERATION_SUCCESS,SUCCESS_MESSAGE);
    }
}
