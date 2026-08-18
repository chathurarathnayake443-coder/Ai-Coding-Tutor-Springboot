package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.CodeExecutionResult;
import com.ijse.AI_Coding_Tutor_Springboot.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CodeExecutionServiceImpl implements CodeExecutionService {

    @Value("${judge0.api.url}")
    private String apiUrl;

    @Value("${judge0.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Map<String, Integer> LANGUAGE_IDS = Map.of(
            "java", 62,
            "python", 71,
            "javascript", 63
    );

    public CodeExecutionResult runCode(String language, String code) {
        Integer languageId = LANGUAGE_IDS.get(language.toLowerCase());
        if (languageId == null) {
            throw new RuntimeException("Unsupported language: " + language);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Judge0-Auth-Token", apiToken);

        Map<String, Object> body = new HashMap<>();
        body.put("source_code", code);
        body.put("language_id", languageId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> submitResponse = restTemplate.postForEntity(
                apiUrl + "/submissions?base64_encoded=false&wait=false",
                request,
                Map.class
        );

        String token = (String) submitResponse.getBody().get("token");

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            HttpEntity<Void> pollRequest = new HttpEntity<>(headers);
            ResponseEntity<Map> pollResponse = restTemplate.exchange(
                    apiUrl + "/submissions/" + token + "?base64_encoded=false",
                    HttpMethod.GET,
                    pollRequest,
                    Map.class
            );

            Map<String, Object> result = pollResponse.getBody();
            Map<String, Object> status = (Map<String, Object>) result.get("status");
            int statusId = (int) status.get("id");

            // statusId 1 = queued, 2 = processing — keep polling until done
            if (statusId != 1 && statusId != 2) {
                String stdout = (String) result.get("stdout");
                String stderr = (String) result.get("stderr");
                String compileOutput = (String) result.get("compile_output");

                if (stderr != null) {
                    return new CodeExecutionResult(false, stderr);
                }
                if (compileOutput != null) {
                    return new CodeExecutionResult(false, compileOutput);
                }
                return new CodeExecutionResult(true, stdout != null ? stdout : "(no output)");
            }
        }

        return new CodeExecutionResult(false, "Execution timed out.");
    }
}
