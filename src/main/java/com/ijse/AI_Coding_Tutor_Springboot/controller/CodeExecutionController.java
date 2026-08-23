package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionRequest;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionResult;
import com.ijse.AI_Coding_Tutor_Springboot.service.GeminiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
public class CodeExecutionController {

    private final GeminiService geminiService;

    public CodeExecutionController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/runCode")
    public CommonResponse runCode(@RequestBody CodeExecutionRequest codeExecutionRequest){
        try{
            CodeExecutionResult codeExecutionResult = geminiService.executeCode(codeExecutionRequest.getSessionId(),codeExecutionRequest.getCode(), codeExecutionRequest.getLanguage());
            return new CommonResponse(OPERATION_SUCCESS,codeExecutionResult,SUCCESS_MESSAGE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
