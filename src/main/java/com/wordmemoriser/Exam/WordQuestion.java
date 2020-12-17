package com.wordmemoriser.Exam;

import com.wordmemoriser.Word.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordQuestion {

    private String question;

    private String answer;

    private HashMap<String,Boolean> options;

    private int wordId;

    private Language askedLang;

    private int point = 0;

    private ExamType questionType;

    private ExamType answerType;

    public void addOption(String question, boolean isCorrect){
        options.put(question,isCorrect);
    }

}
