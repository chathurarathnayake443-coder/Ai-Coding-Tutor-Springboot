package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.genai.types.GenerateContentResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionResult;
import com.ijse.AI_Coding_Tutor_Springboot.dto.HintRequestDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.HintResponseDTO;

public interface GeminiService {

    public String generateResponse(String prompt);

    public CodeExecutionResult executeCode(String code, String language) throws JsonProcessingException;

    public HintResponseDTO generateHint(HintRequestDTO hintRequestDTO) throws JsonProcessingException;
}
