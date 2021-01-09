package com.wordmemoriser.Exam;

import com.wordmemoriser.Word.Language;
import com.wordmemoriser.Word.Word;
import com.wordmemoriser.Word.WordService;
import com.wordmemoriser.WordMeaning.WordMeaningService;
import com.wordmemoriser.WordValue.WordValueService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    static Logger logger = LogManager.getLogger(ExamService.class);

    public HttpStatus updateWordPoint(Integer wordId, Integer point){
        logger.info("[updateWordPoint] Request Received.");
        logger.log(Level.getLevel("INTERNAL"),"[updateWordPoint] wordId = " + wordId + ", point: " + point);

        Boolean updateResult = wordService.updateWordPoint(wordId, point);
        logger.info("[updateWordPoint] Update Result is: " + updateResult);
        return updateResult ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
    }

    public ResponseEntity<List<WordQuestion>> getQuestionWords(ExamRequestTemplate examRequestTemplate){
        logger.info("[getQuestionWords] Request Received.");
        logger.log(Level.getLevel("INTERNAL"),"[getQuestionWords] ExamRequestTemplate = " + examRequestTemplate.toString());

        relatedRepositoryControls();
        if(countControls()){
            List<WordQuestion> wordQuestions = generateQuestions(examRequestTemplate.questionType,examRequestTemplate.answerType,examRequestTemplate.language,examRequestTemplate.lowerLimit,examRequestTemplate.upperLimit);
            logger.log(Level.getLevel("INTERNAL"),"[getQuestionWords] Created word Questions: " + wordQuestions.toString());
            if(wordQuestions != null){
                Collections.shuffle(wordQuestions);
                return new ResponseEntity<>(wordQuestions, HttpStatus.OK);
            }
            else {
                logger.warn("[getQuestionWords] Word Questions List is null, returning NOT_ACCEPTABLE");
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else {
            logger.warn("[getQuestionWords] Count Controls weren't passed, returning NOT_ACCEPTABLE");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private void relatedRepositoryControls(){
        wordValueService.setRepository();
        wordMeaningService.setRepository();

        logger.info("[relatedRepositoryControls] WordMeaning and WordValues are synchoronized with db.");
    }

    private Boolean countControls(){
        int minWordValueCount = wordValueService.getMinWordValueCount();
        int minWordMeaningCount = wordMeaningService.getMinWordMeaningCount();
        if(Math.min(minWordValueCount, minWordMeaningCount) < 5){
            logger.warn("[countControls] minWordValueCount or minWordMeaningCount is less than 5, returning false.");
            logger.warn("[countControls] minWordValueCount = " + minWordValueCount + ", minWordMeaningCount = " +minWordMeaningCount);
            return false;
        }
        logger.log(Level.getLevel("INTERNAL"),"[countControls] Counts are higher than 5, returning true.");
        return true;
    }

    private List<WordQuestion> generateQuestions(ExamType questionType, ExamType answerType, Language askedLanguage, int lowerLimit, int upperLimit){
        List<Word> words = wordService.getLimitedWords(lowerLimit,upperLimit);
        Language answerLanguage = askedLanguage.equals(Language.TR) ? Language.EN : Language.TR;

        logger.log(Level.getLevel("INTERNAL"),"[generateQuestions Entered generateQuestions with values:");
        logger.log(Level.getLevel("INTERNAL"),"- Question Type: " + questionType);
        logger.log(Level.getLevel("INTERNAL"),"- Answer Type: " + answerType);
        logger.log(Level.getLevel("INTERNAL"),"- Asked Language: " + askedLanguage);
        logger.log(Level.getLevel("INTERNAL"),"- Answer Language: " + answerLanguage);
        logger.log(Level.getLevel("INTERNAL"),"- Lower Limit: " + lowerLimit);
        logger.log(Level.getLevel("INTERNAL"),"- Upper Limit: " + upperLimit);
        logger.log(Level.getLevel("INTERNAL"),"[generateQuestions] Limited words size: " + words.size());

        if(null != words && words.size()>5){
            List<WordQuestion> wordQuestions = new ArrayList<>();
            for (Word word:words) {
                WordQuestion wordQuestion = createQuestion(word,questionType,answerType,askedLanguage,answerLanguage);
                wordQuestions.add(wordQuestion);
                logger.log(Level.getLevel("DEEPER"),"[generateQuestions] Created Word Question: " + wordQuestion.toString());
            }
            return wordQuestions;
        }
        else {
            logger.warn("[generateQuestions] words size is less than 5 or words is null, returning null.");
            return null;
        }

    }
    public WordQuestion createQuestion(Word word, ExamType questionType, ExamType answerType, Language askedLanguage, Language answerLanguage){
        String questionValue = word.getValue(questionType,askedLanguage);
        String answerValue =  word.getValue(answerType,answerLanguage);

        logger.log(Level.getLevel("DEEPER"),"[createQuestion] " + " Entered generateQuestions with values:");
        logger.log(Level.getLevel("DEEPER"),"- Question Value: " + questionValue);
        logger.log(Level.getLevel("DEEPER"),"- Answer Value: " + answerValue);
        logger.log(Level.getLevel("DEEPER"),"- Word: " + word.toString());

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

        logger.log(Level.getLevel("DEEPER"),"[createQuestion] falseOptions: " + falseOptions.toString());

        for (String falseOption:falseOptions) {
            wordQuestion.addOption(falseOption,false);
        }
        return  wordQuestion;
    }
}
