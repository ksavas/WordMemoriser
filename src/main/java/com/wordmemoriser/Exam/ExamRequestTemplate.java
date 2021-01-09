package com.wordmemoriser.Exam;

import com.wordmemoriser.Word.Language;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExamRequestTemplate {

    public ExamType questionType;

    public ExamType answerType;

    public Language language;

    public int lowerLimit;

    public int upperLimit;

    public String toString(){
        return "{ QuestionType: " + questionType + ", AnswerType: " + answerType
                + ", Language: " + language + ", LowerLimit: " + lowerLimit
                + ", UpperLimit: " + upperLimit + " }";
    }

}
