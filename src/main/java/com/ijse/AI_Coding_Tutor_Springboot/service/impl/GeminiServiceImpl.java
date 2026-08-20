package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionResult;
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
}
