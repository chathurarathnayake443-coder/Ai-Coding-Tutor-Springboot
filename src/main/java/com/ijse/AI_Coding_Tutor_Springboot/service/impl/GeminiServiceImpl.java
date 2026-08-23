package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.*;
import com.ijse.AI_Coding_Tutor_Springboot.entity.*;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.ExecutionStatus;
import com.ijse.AI_Coding_Tutor_Springboot.repository.CodeAttemptRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.CodingSessionRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.HintRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.SolutionRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.GeminiService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeminiServiceImpl implements GeminiService {

    private final Client geminiClient;
    private final ObjectMapper objectMapper;
    private final CodeAttemptRepository codeAttemptRepository;
    private final CodingSessionRepository codingSessionRepository;
    private final HintRepository hintRepository;
    private final SolutionRepository solutionRepository;

    public GeminiServiceImpl(Client geminiClient, ObjectMapper objectMapper, CodeAttemptRepository codeAttemptRepository, CodingSessionRepository codingSessionRepository, HintRepository hintRepository, SolutionRepository solutionRepository) {
        this.geminiClient = geminiClient;
        this.objectMapper = objectMapper;
        this.codeAttemptRepository = codeAttemptRepository;
        this.codingSessionRepository = codingSessionRepository;
        this.hintRepository = hintRepository;
        this.solutionRepository = solutionRepository;
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

    public CodeExecutionResult executeCode(long sessionId,String code, String language) throws JsonProcessingException {
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

            CodeExecution codeExecution = new CodeExecution();
            codeExecution.setErrorMessage(codeResult.getError());
            codeExecution.setExecutedTime(LocalDateTime.now());
            codeExecution.setExecutionStatus(ExecutionStatus.SUCCESSFUL);
            codeExecution.setOutput(codeResult.getOutput());

            CodeAttempt codeAttempt = new CodeAttempt();

            Integer lastAttempt = codeAttemptRepository.getAttemptNumberBySessionId(sessionId);
            int nextAttemptNumber = (lastAttempt == null) ? 1 : lastAttempt + 1;
            codeAttempt.setAttemptNumber(nextAttemptNumber);
            codeAttempt.setCode(code);
            codeAttempt.setSubmittedTime(LocalDateTime.now());

            Optional<CodingSession> optionalCodingSession = codingSessionRepository.findById(sessionId);
            if (!optionalCodingSession.isPresent()) {
                throw new RuntimeException("Sorry, session not found");
            }
            CodingSession codingSession = optionalCodingSession.get();
            codeAttempt.setCodingSession(codingSession);

            List<CodeExecution> codeExecutions = new ArrayList<>();
            codeExecutions.add(codeExecution);

            codeAttempt.setCodeExecutions(codeExecutions);
            codeExecution.setCodeAttempt(codeAttempt);
            codeAttemptRepository.save(codeAttempt);
            return codeResult;
        }
        catch(Exception e){
            throw e;
        }
    }

    public HintResponseDTO generateHint(HintRequestDTO hintRequestDTO) throws JsonProcessingException {
        try {
            System.out.println("========== HINT REQUEST ==========");
            System.out.println("Session ID: " + hintRequestDTO.getSessionId());
            System.out.println("Hint Number: " + hintRequestDTO.getHintNumber());
            System.out.println("Language: " + hintRequestDTO.getLanguage());
            System.out.println("Problem: " + hintRequestDTO.getCodingProblem());
            System.out.println("Student Code: " + hintRequestDTO.getStudentCode());
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

            Hint hint = new Hint();
            hint.setCreatedTime(LocalDateTime.now());
            hint.setHintNumber(hintRequestDTO.getHintNumber());
            hint.setHintText(hintResult.getHint());

            Optional<CodingSession> optionalCodingSession = codingSessionRepository.findById(hintRequestDTO.getSessionId());
            if (!optionalCodingSession.isPresent()) {
                throw new RuntimeException("Sorry, session not found");
            }
            CodingSession codingSession = optionalCodingSession.get();
            hint.setCodingSession(codingSession);
            hintRepository.save(hint);
            return hintResult;
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseSolutionDTO generateActualSolution(RequestSolutionDTO requestSolutionDTO) throws JsonProcessingException {
        try{
            String prompt = """
        You are an AI Coding Tutor.

        The student has used all 5 available hints.
        The student can now see the complete solution to the coding problem.

        Generate the correct and complete solution for the coding problem
        using the specified programming language.

        IMPORTANT RULES:
        1. Solve the coding problem completely and correctly.
        2. Use ONLY the specified programming language.
        3. The solution must be complete and executable.
        4. Follow all requirements in the coding problem.
        5. Return the response as a JSON object.
        6. The JSON object must contain ONLY one field named "solutionCode".
        7. The value of "solutionCode" must contain the complete source code.
        8. Do NOT include any explanation.
        9. Do NOT include Markdown code fences.
        10. Do NOT include any additional fields.

        Required response format:
        {
            "solutionCode": "complete solution code here"
        }

        Programming Language:
        %s

        Coding Problem:
        %s

        Generate the final solution now.
        """.formatted(requestSolutionDTO.getLanguage(), requestSolutionDTO.getCodingProblem());

            String response = generateResponse(prompt);
            System.out.println(response);

            ResponseSolutionDTO responseSolutionDTO = objectMapper.readValue(response, ResponseSolutionDTO.class);
            System.out.println(responseSolutionDTO);

            Solution solution = new Solution();
            solution.setGeneratedTime(LocalDateTime.now());
            solution.setSolutionCode(responseSolutionDTO.getSolutionCode());

            Optional<CodingSession> optionalCodingSession = codingSessionRepository.findById(requestSolutionDTO.getSessionId());
            if (!optionalCodingSession.isPresent()) {
                throw new RuntimeException("Sorry, session not found");
            }
            CodingSession codingSession = optionalCodingSession.get();
            solution.setCodingSession(codingSession);
            solutionRepository.save(solution);

            return responseSolutionDTO;
        }
        catch(Exception e){
            throw e;
        }
    }
}
