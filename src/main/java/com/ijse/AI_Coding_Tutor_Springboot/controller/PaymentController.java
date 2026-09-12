package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.PaymentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Payment;
import com.ijse.AI_Coding_Tutor_Springboot.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/processPayment")
    public CommonResponse processPayment(@RequestBody PaymentDTO paymentDTO){
        paymentService.processPayment(paymentDTO);
        return new CommonResponse(OPERATION_SUCCESS,SUCCESS_MESSAGE);
    }
}
