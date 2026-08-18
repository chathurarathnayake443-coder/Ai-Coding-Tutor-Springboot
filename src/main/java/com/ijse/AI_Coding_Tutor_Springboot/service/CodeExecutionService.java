package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionResult;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

public interface CodeExecutionService {

    public CodeExecutionResult runCode(String language, String code);
}
