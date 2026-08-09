package com.ijse.AI_Coding_Tutor_Springboot.entity;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.LanguageAvailability;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProgrammingLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long languageId;
    private String languageName;

    @Enumerated(EnumType.STRING)
    private LanguageAvailability languageAvailability;

    @OneToMany(mappedBy = "programmingLanguage")
    private List<CodingSession> codingSessions;
}
