package com.ijse.AI_Coding_Tutor_Springboot.controller;


import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionRequest;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionResult;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.service.CodeExecutionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    public CodeExecutionController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping("/runCode")
    public CommonResponse runCode(@RequestBody CodeExecutionRequest codeExecutionRequest){
        CodeExecutionResult codeExecutionResult = codeExecutionService.runCode(codeExecutionRequest.getLanguage(), codeExecutionRequest.getCode());
        return new CommonResponse(OPERATION_SUCCESS,codeExecutionResult,SUCCESS_MESSAGE);
    }
}
