package com.ijse.AI_Coding_Tutor_Springboot.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomException extends RuntimeException{

    private int status;
    private String message;
}
