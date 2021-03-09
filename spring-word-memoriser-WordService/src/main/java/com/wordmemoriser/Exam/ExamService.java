package com.wordmemoriser.Exam;

import com.wordmemoriser.Word.Language;
import com.wordmemoriser.Word.Word;
import com.wordmemoriser.Word.WordService;
import com.wordmemoriser.WordMeaning.WordMeaningService;
import com.wordmemoriser.WordValue.WordValueService;
import com.wordmemoriser.account.AccountService;
import com.wordmemoriser.account.AccountWordPointService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamService {

    @Autowired
    WordService wordService;

    @Autowired
    WordValueService wordValueService;

    @Autowired
    WordMeaningService wordMeaningService;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountWordPointService accountWordPointService;

    static Logger logger = LogManager.getLogger(ExamService.class);

    public HttpStatus updateWordPoint(Integer remoteId, Integer wordId, Integer point){
        logger.info("[updateWordPoint] Request Received.");
        return accountWordPointService.updateAccountWordPoint(remoteId,wordId,point);
    }

    public ResponseEntity<List<WordQuestion>> getQuestionWords(ExamRequestTemplate examRequestTemplate){
        logger.info("[getQuestionWords] Request Received.");
        logger.log(Level.getLevel("INTERNAL"),"[getQuestionWords] ExamRequestTemplate: " + examRequestTemplate.toString());

        if(countControls()){
            Set<Word> relatedWords = accountService.getWordsByAccount(examRequestTemplate.remoteId,examRequestTemplate.upperLimit, examRequestTemplate.lowerLimit);
            List<WordQuestion> wordQuestions = generateQuestions(relatedWords, examRequestTemplate.questionType,examRequestTemplate.answerType,examRequestTemplate.language,examRequestTemplate.lowerLimit,examRequestTemplate.upperLimit);
            if(wordQuestions != null){
                logger.log(Level.getLevel("INTERNAL"),"[getQuestionWords] Created word Questions: " + wordQuestions.toString());
                Collections.shuffle(wordQuestions);
                return new ResponseEntity<>(wordQuestions, HttpStatus.OK);
            }
            else {
                logger.warn("[getQuestionWords] Word Questions List is null, returning HTTP 406: NOT_ACCEPTABLE");
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else {
            logger.warn("[getQuestionWords] there is not enough words for account Id:" + examRequestTemplate.remoteId +", returning HTTP 406: NOT_ACCEPTABLE");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private Boolean countControls(){
        relatedRepositoryControls();
        int minWordValueCount = wordValueService.getMinWordValueCount();
        int minWordMeaningCount = wordMeaningService.getMinWordMeaningCount();
        if(Math.min(minWordValueCount, minWordMeaningCount) < 5){
            logger.warn("[countControls] minWordValueCount or minWordMeaningCount is less than 5, returning false.");
            logger.warn("[countControls] minWordValueCount: " + minWordValueCount + ", minWordMeaningCount: " +minWordMeaningCount);
            return false;
        }
        logger.log(Level.getLevel("INTERNAL"),"[countControls] Counts are higher than 5, returning true.");
        return true;
    }

    private void relatedRepositoryControls(){
        wordValueService.setRepository();
        wordMeaningService.setRepository();

        logger.info("[relatedRepositoryControls] WordMeaning and WordValues are synchoronized with db.");
    }


    private List<WordQuestion> generateQuestions(Set<Word> relatedWords, ExamType questionType, ExamType answerType, Language askedLanguage, int lowerLimit, int upperLimit){
        Language answerLanguage = askedLanguage.equals(Language.TR) ? Language.EN : Language.TR;

        logger.log(Level.getLevel("INTERNAL"),"[generateQuestions] Entered generateQuestions with values:");
        logger.log(Level.getLevel("INTERNAL"),"- Question Type: " + questionType);
        logger.log(Level.getLevel("INTERNAL"),"- Answer Type: " + answerType);
        logger.log(Level.getLevel("INTERNAL"),"- Asked Language: " + askedLanguage);
        logger.log(Level.getLevel("INTERNAL"),"- Answer Language: " + answerLanguage);
        logger.log(Level.getLevel("INTERNAL"),"- Lower Limit: " + lowerLimit);
        logger.log(Level.getLevel("INTERNAL"),"- Upper Limit: " + upperLimit);
        logger.log(Level.getLevel("INTERNAL"),"[generateQuestions] Limited related words size: " + relatedWords.size());

        if(null != relatedWords){
            List<WordQuestion> wordQuestions = new ArrayList<>();
            for (Word word:relatedWords) {
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
