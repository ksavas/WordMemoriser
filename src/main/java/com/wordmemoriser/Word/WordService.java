package com.wordmemoriser.Word;

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

import java.util.*;
import java.util.stream.Collectors;

/*
This class is like the main class that handle whole WordMemoriser mechanism.
All the Word tables(Word,WordValue,WordMeaning tables) are related with each other and
when the user wants to insert a new word the user must insert related WordValue and WordMeaning too
So the inserting steps in this mechanism is:
1. Checks the WordValues are exist or not and if not inserts new WordValues.
2. Checks the word is exist in db or not:
   - Checks the Turkish and English WordValues have common words or not, if both Turkish and English WordValues have common Words the new word might be exist in the db.
   - So when the common words are found this mechanism sends back the existing Words and related WordMeanings to the user and asks him to really want or not.
   - When the user gets the existing Words and related WordMeanings he checks the his current Word exist or not and if not, the user sends to back his new word with
     forceSave parameter which is used for no matter is the word exist or not, the word must be saved to the db.
3. checks the WordMeaning is exist or not.
4. Save the word with WordValues and WordMeaning
 */

@Service
public class WordService {

    @Autowired
    private WordRespository wordRespository;

    @Autowired
    private WordValueService wordValueService;

    @Autowired
    private WordMeaningService wordMeaningService;

    public ResponseEntity<List<WordMeaning>> saveWordIfNotExist(WordTemplate wordTemplate){

        WordValueHolder wordValueHolder = wordValueControls(wordTemplate.trWordValue,wordTemplate.enWordValue);

        List<Word> words = wordControls(wordValueHolder.getTrWordValue(),wordValueHolder.getEnWordValue());

        WordMeaningHolder wordMeaningHolder = wordMeaningControls(wordTemplate);

        if(wordValueHolder.isTrWordValueExist()
                && wordValueHolder.isEnWordValueExist()
                && wordMeaningHolder.getIsExist()
                && !wordTemplate.isForceSave()
                && words.size()>0){
            List<WordMeaning> wordMeanings = wordMeaningService.findWordMeaningsByWords(words);
            return new ResponseEntity<List<WordMeaning>>(wordMeanings, HttpStatus.CONFLICT);
        }
        else {
            Word newWord = saveNewWord(wordValueHolder.getTrWordValue(),wordValueHolder.getEnWordValue(),wordMeaningHolder.getWordMeaning(),wordTemplate.wordType);
            return new ResponseEntity<List<WordMeaning>>(Arrays.asList(newWord.getWordMeaning()), HttpStatus.OK);
        }
    }

    private WordValueHolder wordValueControls(String trWordValue, String enWordValue){
        return wordValueService.SaveWordValuesIfNotExist(trWordValue,enWordValue);
    }

    private List<Word> wordControls(WordValue trWordValue, WordValue enWordValue){

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

    private Set<Integer> getWordIds(WordValue wordValue){
        return wordValue
                .getTrMeantWords()
                .stream()
                .map(word -> word.getId())
                .collect(Collectors.toSet());
    }

    private WordMeaningHolder wordMeaningControls(WordTemplate wordTemplate){
        return wordMeaningService.saveWordMeaning(wordTemplate);
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

}
