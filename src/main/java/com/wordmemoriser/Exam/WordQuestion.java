package com.wordmemoriser.Exam;

import com.wordmemoriser.Word.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordQuestion {

    static Logger logger = LogManager.getLogger(WordQuestion.class);
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
        logger.log(Level.getLevel("DEEPER"),"[addOption] New Option was added: Question: " + question + ", isCorrect: " + isCorrect);
        logger.log(Level.getLevel("DEEPER"),"[addOption] New elements of HashMap 'options' : [" + optionstoString() + "]");
    }

    private String optionstoString(){
        return options.entrySet()
                .stream()
                .map(e -> "{" + e.getKey() + ": "+e.getValue() + "}")
                .collect(Collectors.joining(","));
    }

    @Override
    public String toString(){
        return "{ Question: " + question + ", Answer: " + answer + ", Options: " + optionstoString() + ", Word Id: " + wordId
                + ", Asked Language: " + askedLang + ", Point: " + point + ", Question Type: " + questionType + ", Answer Type: " + answerType + " }";
    }

}
