package com.wordmemoriser.Exam;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExamResult {

    private String question;

    private String answer;

    private boolean isCorrect;

}
