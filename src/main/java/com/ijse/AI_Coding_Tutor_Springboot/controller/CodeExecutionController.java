package com.ijse.AI_Coding_Tutor_Springboot.controller;


import com.ijse.AI_Coding_Tutor_Springboot.service.CodeExecutionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    public CodeExecutionController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }
}
