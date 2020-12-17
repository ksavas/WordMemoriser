package com.wordmemoriser.Exam;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExamResultHolder {

    private List<ExamResult> examResults;

    private int totalCorrectAnswer;

    private int totalFalseAnswer;

    private float successPercentage;


}
