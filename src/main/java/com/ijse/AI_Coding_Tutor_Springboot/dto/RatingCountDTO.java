package com.ijse.AI_Coding_Tutor_Springboot.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RatingCountDTO {
    private int ratingValue;
    private long studentCount;
}
