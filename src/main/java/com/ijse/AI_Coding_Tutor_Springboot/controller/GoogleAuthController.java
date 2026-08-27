package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GoogleAuthRequestDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.UserDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.UserDataDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.security.JwtUtil;
import com.ijse.AI_Coding_Tutor_Springboot.service.StudentService;
import com.ijse.AI_Coding_Tutor_Springboot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_FAILURE;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/auth")
public class GoogleAuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil; // whatever generates your existing JWTs

    @Value("${google.client.id}")
    private String googleClientId;

    public GoogleAuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/google")
    public ResponseEntity<CommonResponse> googleSignIn(@RequestBody GoogleAuthRequestDTO dto) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(dto.getIdToken());
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new CommonResponse(OPERATION_FAILURE, null, "Invalid Google token"));
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            // find existing student by email, or create a new one on first login
            UserDTO userDetails = userService.findOrCreateByGoogleEmail(email, name);

            String token = jwtUtil.generateToken(userDetails);

            UserDataDTO userDataDTO = new UserDataDTO(userDetails.getUserId(),token);
            return ResponseEntity.ok(new CommonResponse(OPERATION_SUCCESS, userDataDTO, SUCCESS_MESSAGE));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse(OPERATION_FAILURE, null, e.getMessage()));
        }
    }
}
