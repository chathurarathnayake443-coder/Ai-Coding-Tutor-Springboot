package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionResult;
import com.ijse.AI_Coding_Tutor_Springboot.dto.HintRequestDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.HintResponseDTO;
import com.ijse.AI_Coding_Tutor_Springboot.service.GeminiService;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl implements GeminiService {

    private final Client geminiClient;
    private final ObjectMapper objectMapper;

    public GeminiServiceImpl(Client geminiClient, ObjectMapper objectMapper) {
        this.geminiClient = geminiClient;
        this.objectMapper = objectMapper;
    }

    public String generateResponse(String prompt) {

        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-3.5-flash",
                        prompt,
                        null
                );

        return response.text();
    }

    public CodeExecutionResult executeCode(String code, String language) throws JsonProcessingException {
        try{
            String prompt = """
            You are a programming code execution and analysis assistant.

            The student has submitted a program written in the following programming language:

            Programming Language:
            %s

            Student's Code:
            -------------------------
            %s
            -------------------------

            Analyze the submitted program and determine what would happen
            when this program is executed.

            If the program executes successfully, provide the expected
            console output.

            If the program contains a syntax error, compilation error,
            runtime error, or another execution problem, explain the error
            instead.

            Return your response as a JSON object with exactly these fields:

            {
              "output": "the expected console output",
              "error": "error description if there is an error, otherwise null"
            }

            Do not include Markdown.
            Do not include ```json.
            Do not include any additional text outside the JSON object.
            """.formatted(language, code);

            String response = generateResponse(prompt);
            System.out.println(response);

            CodeExecutionResult codeResult =
                    objectMapper.readValue(response, CodeExecutionResult.class);

            System.out.println(codeResult);
            return codeResult;
        }
        catch(Exception e){
            throw e;
        }
    }

    public HintResponseDTO generateHint(HintRequestDTO hintRequestDTO) throws JsonProcessingException {
        try{
            String prompt = """
        You are an AI programming tutor helping a student learn programming.

        Your job is to analyze the student's CURRENT CODE and provide a
        helpful hint based on what they have written so far.

        Programming Language:
        %s

        Problem the student is trying to solve:
        -------------------------
        %s
        -------------------------

        Student's current code:
        -------------------------
        %s
        -------------------------

        Analyze the student's current implementation carefully.

        Your response must follow these rules:

        1. Identify the most important issue or missing step in the
           student's current code.
        2. Give ONE clear and progressive hint.
        3. Do NOT provide the complete solution.
        4. Do NOT rewrite the student's code.
        5. Do NOT directly give the final answer.
        6. Do NOT give multiple hints at once.
        7. Consider what the student has already accomplished.
        8. If the code is partially correct, acknowledge the correct
           approach and guide the student toward the next step.
        9. If there is a syntax or logical error, guide the student
           toward finding it rather than simply fixing it.
        10. Keep the hint short and easy for a student to understand.

        Return the response as a JSON object in exactly this format:

        {
            "hint": "Your single helpful hint here"
        }

        Do not include Markdown.
        Do not include ```json.
        Do not include any text outside the JSON object.
        """.formatted(
                    hintRequestDTO.getLanguage(),
                    hintRequestDTO.getCodingProblem(),
                    hintRequestDTO.getStudentCode()
            );

            String response = generateResponse(prompt);
            System.out.println(response);

            HintResponseDTO hintResult =
                    objectMapper.readValue(response, HintResponseDTO.class);

            System.out.println(hintResult);
            return hintResult;
        }
        catch(Exception e){
            throw e;
        }
    }

//    public RequestSolutionDTO generateActualSolution(){
//
//    }
}
