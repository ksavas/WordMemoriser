package com.wordmemoriser.Word;

import com.wordmemoriser.Exam.ExamType;
import com.wordmemoriser.User.User;
import com.wordmemoriser.User.UserRepository;
import com.wordmemoriser.User.UserWordPoint;
import com.wordmemoriser.User.UserWordPointRepository;
import com.wordmemoriser.WordMeaning.WordMeaning;
import com.wordmemoriser.WordMeaning.WordMeaningHolder;
import com.wordmemoriser.WordMeaning.WordMeaningService;
import com.wordmemoriser.WordValue.WordValue;
import com.wordmemoriser.WordValue.WordValueHolder;
import com.wordmemoriser.WordValue.WordValueService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class WordService {

    @Autowired
    private WordRespository wordRespository;

    @Autowired
    private WordValueService wordValueService;

    @Autowired
    private WordMeaningService wordMeaningService;

    Logger logger = LogManager.getLogger(WordService.class);

    public Boolean updateWordPoint(Integer userId, Integer wordId, Integer point){
        Optional<Word> _word = wordRespository
                .findAll()
                .stream()
                .filter(_wordValue -> _wordValue.getId() == wordId)
                .findFirst();
        if(_word.isPresent()){
            Word word = _word.get();
            logger.log(Level.getLevel("INTERNAL"),"[updateWordPoint] The word has been found in db, word: " + word);
            word.setPoint(userId, point);
            wordRespository.save(word);
            return true;
        }
        return false;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserWordPointRepository userWordPointRepository;

    public ResponseEntity<List<WordTemplate>> getAllWords(){

        User user = User
                .builder()
                .relatedId(2)
                .userName("ksvs")
                .userWordPoints(new HashSet<>())
                .build();

        userRepository.save(user);

        List<Word> _words = wordRespository.findAll();

        for (Word word: _words) {
            word.setUserWordPoints(new HashSet<>());
            wordRespository.save(word);
            UserWordPoint userWordPoint = UserWordPoint.builder()
                    .user(user)
                    .word(word)
                    .point(word.getPoint())
                    .build();

            userWordPointRepository.save(userWordPoint);
        }

        List<UserWordPoint> _userWordPoints = userWordPointRepository.findAll();

        for (Word word: wordRespository.findAll()) {
            HashSet<UserWordPoint> userWordPoints = (HashSet<UserWordPoint>) word.getUserWordPoints();
            logger.info(userWordPoints.size());
        }

        User _user = userRepository.findAll().stream().filter(x -> x.getId() > 0).findFirst().get();
        logger.info(_user.getUserWordPoints().size());

        return new ResponseEntity<>(generateWordPointTemplates(wordRespository.findAll()), HttpStatus.OK);
    }
    private List<WordTemplate> generateWordPointTemplates(List<Word> words){
        List<WordTemplate> wordPointTemplates = new ArrayList<>();
        for (Word word:words) {
            wordPointTemplates.add(WordTemplate
                    .builder()
                    .id(word.getId())
                    .trWordValue(word.getTrWordValue().getValue())
                    .enWordValue(word.getEnWordValue().getValue())
                    .trMeaning(word.getWordMeaning().getTurkishMeaning())
                    .enMeaning(word.getWordMeaning().getEnglishMeaning())
                    .wordType(word.getWordType())
                    .point(word.getPoint())
                    .build());
        }
        return wordPointTemplates;
    }

    public ResponseEntity<List<WordTemplate>> deleteWord(String strId){
        try{
            logger.info("[deleteWord] Request Received. Word Id: " + strId);
            Integer wordId = Integer.parseInt(strId);
            Optional<Word> optionalWord = wordRespository
                    .findAll()
                    .stream()
                    .filter(_wordValue -> _wordValue.getId() == wordId)
                    .findFirst();

            if(optionalWord.isPresent()){
                Word word = optionalWord.get();
                WordValue trWordValue =  word.getTrWordValue();
                WordValue enWordValue = word.getEnWordValue();
                WordMeaning wordMeaning = word.getWordMeaning();

                logger.log(Level.getLevel("INTERNAL"),"[deleteWord] The Word exists in db, Values:");
                logger.log(Level.getLevel("INTERNAL"),"- Word: " + word.toString());
                logger.log(Level.getLevel("INTERNAL"),"- Tr Word Value: " + trWordValue.toString());
                logger.log(Level.getLevel("INTERNAL"),"- En Word Value: " + enWordValue.toString());
                logger.log(Level.getLevel("INTERNAL"),"- Word Meaning: " + wordMeaning.toString());

                wordRespository.deleteWordById(word.getId());
                wordValueService.deleteWordValueIfChildless(trWordValue);
                wordValueService.deleteWordValueIfChildless(enWordValue);
                wordMeaningService.deleteWordMeaningIfChildless(wordMeaning);

                logger.info("[deleteWord] The word and related WordValue and WordMeanings have been deleted from db successfully");

                List<WordTemplate> resultWordTemplates = generateWordTemplates(wordRespository.findAll());

                logger.log(Level.getLevel("DEEPER"),"New Word Values after delete: " + resultWordTemplates.toString());

                return new ResponseEntity<>(resultWordTemplates, HttpStatus.OK);
            }
        }catch (NumberFormatException e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<WordTemplate>> saveWordIfNotExist(WordTemplate wordRequestTemplate){

        logger.info("[saveWordIfNotExist] Request received.");

        logger.log(Level.getLevel("INTERNAL"),"[getQuestionWords] WordRequestTemplate: " + wordRequestTemplate.toString());

        WordValueHolder wordValueHolder = wordValueControls(wordRequestTemplate.trWordValue,wordRequestTemplate.enWordValue);

        List<Word> intersectedWords = getIntersectedWordIds(wordValueHolder);
        logger.log(Level.getLevel("DEEPER"),"[getQuestionWords] intersectedWords: " + intersectedWords.toString());

        WordMeaningHolder wordMeaningHolder = wordMeaningControls(wordRequestTemplate);
        logger.log(Level.getLevel("DEEPER"),"[getQuestionWords] wordMeaningHolder: " + wordMeaningHolder.toString());

        return wordSaveControls(intersectedWords,wordValueHolder,wordMeaningHolder,wordRequestTemplate.wordType,wordRequestTemplate.isForceSave());
    }
    private WordValueHolder wordValueControls(String trWordValue, String enWordValue){
        return wordValueService.checkWordValues(trWordValue,enWordValue);
    }
    private List<Word> getIntersectedWordIds(WordValueHolder wordValueHolder){

        logger.log(Level.getLevel("INTERNAL"),"[getIntersectedWordIds] Entered getIntersectedWordIds with Values:");
        logger.log(Level.getLevel("INTERNAL"),"- trWordValue: " + wordValueHolder.getTrWordValue());
        logger.log(Level.getLevel("INTERNAL"),"- isTrWordValueExisted: " + wordValueHolder.isTrWordValueExisted());
        logger.log(Level.getLevel("INTERNAL"),"- enWordValue: " + wordValueHolder.getEnWordValue());
        logger.log(Level.getLevel("INTERNAL"),"- isEnWordValueExisted: " + wordValueHolder.isEnWordValueExisted());

        if(wordValueHolder.isTrWordValueExisted() && wordValueHolder.isEnWordValueExisted()){
            Set<Integer> trWordValueIds = wordValueHolder.getTrWordValue()
                    .getTrMeantWords()
                    .stream()
                    .map(word -> word.getId())
                    .collect(Collectors.toSet());

            logger.log(Level.getLevel("DEEPER"),"[getIntersectedWordIds] trWordValueIds: " + trWordValueIds.toString());

            Set<Integer> enWordValueIds =  wordValueHolder.getEnWordValue()
                    .getEnMeantWords()
                    .stream()
                    .map(word -> word.getId())
                    .collect(Collectors.toSet());

            logger.log(Level.getLevel("DEEPER"),"[getIntersectedWordIds] enWordValueIds: " + enWordValueIds.toString());

            Set<Integer> intersectedWordIds = trWordValueIds
                    .stream()
                    .distinct()
                    .filter(enWordValueIds::contains)
                    .collect(Collectors.toSet());

            logger.log(Level.getLevel("DEEPER"),"[getIntersectedWordIds] intersectedWordIds: " + intersectedWordIds.toString());
            return wordRespository.findAllById(intersectedWordIds);
        }
        logger.log(Level.getLevel("INTERNAL"),"[getIntersectedWordIds] One of the Word Values or both of them not exist so returning empty list..");
        return new ArrayList<>();
    }
    private WordMeaningHolder wordMeaningControls(WordTemplate wordRequestTemplate){
        return wordMeaningService.checkWordMeaning(wordRequestTemplate);
    }
    private ResponseEntity<List<WordTemplate>> wordSaveControls(List<Word> intersectedWords, WordValueHolder wordValueHolder, WordMeaningHolder wordMeaningHolder, String wordType, Boolean isForceSave){

        logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Entered wordSaveControls with Values:");
        logger.log(Level.getLevel("INTERNAL"),"- Intersected Words Size: " + intersectedWords.size());
        logger.log(Level.getLevel("INTERNAL"),"- Intersected Words: " + intersectedWords.toString());
        logger.log(Level.getLevel("INTERNAL"),"- WordValueHolder: " + wordValueHolder.toString());
        logger.log(Level.getLevel("INTERNAL"),"- WordMeaningHolder: " + wordMeaningHolder.toString());
        logger.log(Level.getLevel("INTERNAL"),"- Word Type: " + wordType);
        logger.log(Level.getLevel("INTERNAL"),"- Force Save: " + isForceSave);

        if(wordValueHolder.isTrWordValueExisted() &&
           wordValueHolder.isEnWordValueExisted() &&
           intersectedWords.size()>0){

            logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Some Words have same Word Values..");

            Optional<Word> optionalWord = wordRespository
                    .findAll()
                    .stream()
                    .filter(_wordValue -> _wordValue.getWordMeaning().getId() == wordMeaningHolder.getWordMeaning().getId() &&
                                          _wordValue.getTrWordValue().getId() == wordValueHolder.getTrWordValue().getId() &&
                                          _wordValue.getEnWordValue().getId() == wordValueHolder.getEnWordValue().getId() &&
                                          _wordValue.getWordType().equals(wordType))
                    .findFirst();

            if(optionalWord.isPresent()){
                Word word = optionalWord.get();

                logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] The Word exists in db with same Word Values, same " +
                        "Word Meaning and same Word Type, so returning HTTP 403: Forbidden");
                logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Existed Word: " + word.toString());

                List<Word> singleWord = Stream.of(word).collect(Collectors.toList());
                return new ResponseEntity<>(generateWordTemplates(singleWord), HttpStatus.FORBIDDEN);
            }

            if(!isForceSave){
                List<WordTemplate> intersectedWordTemplates = generateWordTemplates(intersectedWords);

                logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Similar words exist in db and forceSave: " + isForceSave + ", so returning HTTP 409: Conflict.");
                logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Similar words: " + intersectedWords.toString());

                return new ResponseEntity<>(intersectedWordTemplates, HttpStatus.CONFLICT);
            }
        }
        WordMeaning wordMeaning =  wordMeaningService.saveWordMeaning(wordMeaningHolder.getWordMeaning());
        WordValue trWordValue = wordValueService.saveWordValue(wordValueHolder.getTrWordValue());
        WordValue enWordValue = wordValueService.saveWordValue(wordValueHolder.getEnWordValue());

        logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Word Meaning has been stored to db, Word Meaning: " + wordMeaning.toString());
        logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Tr Word Value has been stored to db, Word Value: " + trWordValue.toString());
        logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] En Word Value has been stored to db, Word Value: " + enWordValue.toString());

        Word newWord = saveNewWord(trWordValue,enWordValue,wordMeaning,wordType);

        logger.log(Level.getLevel("INTERNAL"),"[wordSaveControls] Word has been stored to db, Word: " + newWord.toString());
        List<Word> singleWord = Stream.of(newWord).collect(Collectors.toList());
        return new ResponseEntity<>(generateWordTemplates(singleWord), HttpStatus.OK);
    }
    private Word saveNewWord(WordValue trWordValue, WordValue enWordValue, WordMeaning wordMeaning, String wordType){

        Word newWord = Word
                .builder()
                .trWordValue(trWordValue)
                .enWordValue(enWordValue)
                .wordMeaning(wordMeaning)
                .wordType(wordType)
                .build();

        trWordValue.addTrMeantWord(newWord);
        enWordValue.addEnMeantWord(newWord);
        wordMeaning.addWord(newWord);

        return wordRespository.save(newWord);
    }

    private List<WordTemplate> generateWordTemplates(List<Word> words){
        List<WordTemplate> wordTemplates = new ArrayList<>();
        for (Word word:words) {
            wordTemplates.add(WordTemplate
                    .builder()
                    .trWordValue(word.getTrWordValue().getValue())
                    .enWordValue(word.getEnWordValue().getValue())
                    .trMeaning(word.getWordMeaning().getTurkishMeaning())
                    .enMeaning(word.getWordMeaning().getEnglishMeaning())
                    .example(word.getWordMeaning().getExample())
                    .wordType(word.getWordType())
                    .build());
        }
        return wordTemplates;
    }

    // These methods below are called by different services.

    public List<Word> getLimitedWords(int lowerLimit, int upperLimit){
        return wordRespository
                .findAll()
                .stream()
                .filter(_word -> _word.getPoint() > lowerLimit && _word.getPoint() < upperLimit)
                .collect(Collectors.toList());
    }

    public List<String> generateFalseOptions(Word word, Language answerLanguage, ExamType answerType){
        logger.log(Level.getLevel("DEEPER"),"[generateFalseOptions] Entered generateFalseOptions with values: ");
        logger.log(Level.getLevel("DEEPER"),"- Word: " + word.toString());
        logger.log(Level.getLevel("DEEPER"),"- Answer Language: " + answerLanguage);
        logger.log(Level.getLevel("DEEPER"),"- Answer Type: " + answerType);

        HashSet<String> returnValue;
        HashSet<Integer> wordIds  = new HashSet<>(word.getTrWordValue().getTrMeantWords().stream().map(x -> x.getId()).collect(Collectors.toList()));
        wordIds.addAll(word.getEnWordValue().getEnMeantWords().stream().map(x -> x.getId()).collect(Collectors.toList()));
        wordIds.addAll(word.getWordMeaning().getWords().stream().map(x -> x.getId()).collect(Collectors.toList()));

        logger.log(Level.getLevel("DEEPER"),"[generateFalseOptions] Elected WordIds: " + wordIds.toString());

        if(answerType.equals(ExamType.WORD)){
            if(answerLanguage.equals(Language.TR)){
                logger.log(Level.getLevel("DEEPER"),"[generateFalseOptions] inside WORD - TR if block.");
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> ! wordIds.contains(x.getId())).map(x -> x.getTrWordValue().getValue()).collect(Collectors.toSet());
            }
            else{
                logger.log(Level.getLevel("DEEPER"),"[generateFalseOptions] inside WORD - EN if block.");
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> !wordIds.contains(x.getId())).map(x -> x.getEnWordValue().getValue()).collect(Collectors.toSet());
            }
        }
        else {
            if(answerLanguage.equals(Language.TR)){
                logger.log(Level.getLevel("DEEPER"),"[generateFalseOptions] inside MEANING - TR if block.");
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> !wordIds.contains(x.getId())).map(x -> x.getWordMeaning().getTurkishMeaning()).collect(Collectors.toSet());
            }
            else{
                logger.log(Level.getLevel("DEEPER"),"[generateFalseOptions] inside MEANING - EN if block.");
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> !wordIds.contains(x.getId())).map(x -> x.getWordMeaning().getEnglishMeaning()).collect(Collectors.toSet());
            }
        }
        return new ArrayList<>(returnValue).subList(0,3);
    }
}
