package com.wordmemoriser.Word;

import com.wordmemoriser.Exam.ExamType;
import com.wordmemoriser.WordMeaning.WordMeaning;
import com.wordmemoriser.WordMeaning.WordMeaningHolder;
import com.wordmemoriser.WordMeaning.WordMeaningService;
import com.wordmemoriser.WordValue.WordValue;
import com.wordmemoriser.WordValue.WordValueHolder;
import com.wordmemoriser.WordValue.WordValueService;
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

    public Boolean updateWordPoint(Integer wordId, Integer point){
        Optional<Word> _word = wordRespository
                .findAll()
                .stream()
                .filter(_wordValue -> _wordValue.getId() == wordId)
                .findFirst();
        if(_word.isPresent()){
            Word word = _word.get();
            word.setPoint(point);
            wordRespository.save(word);
            return true;
        }
        return false;
    }

    public ResponseEntity<List<WordPointTemplate>> getAllWords(){
        return new ResponseEntity<>(generateWordPointTemplates(wordRespository.findAll()), HttpStatus.OK);
    }
    private List<WordPointTemplate> generateWordPointTemplates(List<Word> words){
        List<WordPointTemplate> wordPointTemplates = new ArrayList<>();
        for (Word word:words) {
            wordPointTemplates.add(WordPointTemplate
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

                wordRespository.deleteWordById(word.getId());
                wordValueService.deleteWordValueIfChildless(trWordValue);
                wordValueService.deleteWordValueIfChildless(enWordValue);
                wordMeaningService.deleteWordMeaningIfChildless(wordMeaning);

                List<WordTemplate> resultWordTemplates = generateWordTemplates(wordRespository.findAll());

                return new ResponseEntity<>(resultWordTemplates, HttpStatus.OK);
            }
        }catch (NumberFormatException e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<WordTemplate>> saveWordIfNotExist(WordRequestTemplate wordRequestTemplate){

        WordValueHolder wordValueHolder = wordValueControls(wordRequestTemplate.trWordValue,wordRequestTemplate.enWordValue);

        List<Word> intersectedWords = getIntersectedWordIds(wordValueHolder.getTrWordValue(),wordValueHolder.getEnWordValue());

        WordMeaningHolder wordMeaningHolder = wordMeaningControls(wordRequestTemplate);

        return wordSaveControls(intersectedWords,wordValueHolder,wordMeaningHolder,wordRequestTemplate.wordType,wordRequestTemplate.isForceSave());
    }
    private WordValueHolder wordValueControls(String trWordValue, String enWordValue){
        return wordValueService.SaveWordValuesIfNotExist(trWordValue,enWordValue);
    }
    private List<Word> getIntersectedWordIds(WordValue trWordValue, WordValue enWordValue){

        Set<Integer> trWordValueIds = trWordValue
                .getTrMeantWords()
                .stream()
                .map(word -> word.getId())
                .collect(Collectors.toSet());

        Set<Integer> enWordValueIds =  enWordValue
                .getEnMeantWords()
                .stream()
                .map(word -> word.getId())
                .collect(Collectors.toSet());

        Set<Integer> intersectedWordIds = trWordValueIds
                .stream()
                .distinct()
                .filter(enWordValueIds::contains)
                .collect(Collectors.toSet());

        return wordRespository.findAllById(intersectedWordIds);
    }
    private WordMeaningHolder wordMeaningControls(WordRequestTemplate wordRequestTemplate){
        return wordMeaningService.checkWordMeaning(wordRequestTemplate);
    }
    private ResponseEntity<List<WordTemplate>> wordSaveControls(List<Word> intersectedWords, WordValueHolder wordValueHolder, WordMeaningHolder wordMeaningHolder, String wordType, Boolean isForceSave){
        if(wordValueHolder.isTrWordValueExisted() &&
           wordValueHolder.isEnWordValueExisted() &&
           intersectedWords.size()>0){

            Optional<Word> optionalWord = wordRespository
                    .findAll()
                    .stream()
                    .filter(_wordValue -> _wordValue.getWordMeaning().getId() == wordMeaningHolder.getWordMeaning().getId() &&
                                          _wordValue.getTrWordValue().getId() == wordValueHolder.getTrWordValue().getId() &&
                                          _wordValue.getEnWordValue().getId() == wordValueHolder.getEnWordValue().getId() &&
                                          _wordValue.getWordType().equals(wordType))
                    .findFirst();

            if(optionalWord.isPresent()){
                List<Word> singleWord = Stream.of(optionalWord.get()).collect(Collectors.toList());
                return new ResponseEntity<>(generateWordTemplates(singleWord), HttpStatus.FORBIDDEN);
            }

            if(!isForceSave){
                List<WordTemplate> intersectedWordTemplates = generateWordTemplates(intersectedWords);
                return new ResponseEntity<>(intersectedWordTemplates, HttpStatus.CONFLICT);
            }
        }
        WordMeaning wordMeaning =  wordMeaningService.saveWordMeaning(wordMeaningHolder.getWordMeaning());
        Word newWord = saveNewWord(wordValueHolder.getTrWordValue(),wordValueHolder.getEnWordValue(),wordMeaning,wordType);
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
        HashSet<String> returnValue;
        HashSet<Integer> wordIds  = new HashSet<>(word.getTrWordValue().getTrMeantWords().stream().map(x -> x.getId()).collect(Collectors.toList()));
        wordIds.addAll(word.getEnWordValue().getEnMeantWords().stream().map(x -> x.getId()).collect(Collectors.toList()));
        wordIds.addAll(word.getWordMeaning().getWords().stream().map(x -> x.getId()).collect(Collectors.toList()));

        if(answerType.equals(ExamType.WORD)){
            if(answerLanguage.equals(Language.TR)){
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> ! wordIds.contains(x.getId())).map(x -> x.getTrWordValue().getValue()).collect(Collectors.toSet());
            }
            else{
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> !wordIds.contains(x.getId())).map(x -> x.getEnWordValue().getValue()).collect(Collectors.toSet());
            }
        }
        else {
            if(answerLanguage.equals(Language.TR)){
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> !wordIds.contains(x.getId())).map(x -> x.getWordMeaning().getTurkishMeaning()).collect(Collectors.toSet());
            }
            else{
                returnValue = (HashSet<String>) wordRespository.findAll().stream().filter(x -> !wordIds.contains(x.getId())).map(x -> x.getWordMeaning().getEnglishMeaning()).collect(Collectors.toSet());
            }
        }
        return new ArrayList<>(returnValue).subList(0,3);
    }
}
