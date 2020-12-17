package com.wordmemoriser.Exam;

import com.wordmemoriser.Word.Language;
import com.wordmemoriser.Word.Word;
import com.wordmemoriser.Word.WordService;
import com.wordmemoriser.WordMeaning.WordMeaningService;
import com.wordmemoriser.WordValue.WordValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

@Service
public class ExamService {

    @Autowired
    WordService wordService;

    @Autowired
    WordValueService wordValueService;

    @Autowired
    WordMeaningService wordMeaningService;

    public HttpStatus updateWordPoint(Integer wordId, Integer point){
        Boolean updateResult = wordService.updateWordPoint(wordId, point);
        return updateResult ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
    }

    public ResponseEntity<List<WordQuestion>> getQuestionWords(ExamRequestTemplate examRequestTemplate){
        relatedRepositoryControls();
        if(countControls()){
            List<WordQuestion> wordQuestions = generateQuestions(examRequestTemplate.questionType,examRequestTemplate.answerType,examRequestTemplate.language,examRequestTemplate.lowerLimit,examRequestTemplate.upperLimit);
            if(wordQuestions != null){
                Collections.shuffle(wordQuestions);
                return new ResponseEntity<>(wordQuestions, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    private void relatedRepositoryControls(){
        wordValueService.setRepository();;
        wordMeaningService.setRepository();
    }

    private Boolean countControls(){
        int minWordValueCount = wordValueService.getMinWordValueCount();
        int minWordMeaningCount = wordMeaningService.getMinWordMeaningCount();

        if(Math.min(minWordValueCount, minWordMeaningCount) < 5){
            return false;
        }
        return true;
    }

    private List<WordQuestion> generateQuestions(ExamType questionType, ExamType answerType, Language askedLanguage, int lowerLimit, int upperLimit){
        List<Word> words = wordService.getLimitedWords(lowerLimit,upperLimit);
        if(null != words && words.size()>5){
            Language answerLanguage = askedLanguage.equals(Language.TR) ? Language.EN : Language.TR;
            List<WordQuestion> wordQuestions = new ArrayList<>();
            for (Word word:words) {
                WordQuestion wordQuestion = createQuestion(word,questionType,answerType,askedLanguage,answerLanguage);
                wordQuestions.add(wordQuestion);
            }
            return wordQuestions;
        }
        return null;
    }
    public WordQuestion createQuestion(Word word, ExamType questionType, ExamType answerType, Language askedLanguage, Language answerLanguage){
        String questionValue = word.getValue(questionType,askedLanguage);
        String answerValue =  word.getValue(answerType,answerLanguage);
        WordQuestion wordQuestion = WordQuestion
                .builder()
                .wordId(word.getId())
                .askedLang(askedLanguage)
                .question(questionValue)
                .questionType(questionType)
                .answerType(answerType)
                .options(new HashMap<String, Boolean>(){{
                    put(answerValue,true);
                }})
                .build();

        List<String> falseOptions = wordService.generateFalseOptions(word,answerLanguage,answerType);

        for (String falseOption:falseOptions) {
            wordQuestion.addOption(falseOption,false);
        }
        return  wordQuestion;
    }
}
