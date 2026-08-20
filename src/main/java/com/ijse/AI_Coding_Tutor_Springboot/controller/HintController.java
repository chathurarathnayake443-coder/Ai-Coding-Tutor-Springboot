package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.HintRequestDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.HintResponseDTO;
import com.ijse.AI_Coding_Tutor_Springboot.service.GeminiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
public class HintController {

    private final GeminiService geminiService;

    public HintController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generateHint")
    public CommonResponse generateHint(@RequestBody HintRequestDTO hintRequestDTO) {
        try{
            HintResponseDTO hintResponseDTO = geminiService.generateHint(hintRequestDTO);
            return new CommonResponse(OPERATION_SUCCESS,hintResponseDTO,SUCCESS_MESSAGE);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
