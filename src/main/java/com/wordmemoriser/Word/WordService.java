package com.wordmemoriser.service;

import com.wordmemoriser.model.Word;
import com.wordmemoriser.model.WordTemplate;
import com.wordmemoriser.model.WordValue;
import com.wordmemoriser.model.WordValueHolder;
import com.wordmemoriser.repository.WordRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordService {

    @Autowired
    private WordRespository wordRespository;

    @Autowired
    private WordValueService wordValueService;


    public ResponseEntity<List<Word>> saveWordIfNotExist(WordTemplate wordTemplate){
        WordValueHolder wordValueHolder =  wordValueService.WordValueControls(wordTemplate.trWordValue,wordTemplate.enWordValue);;

        List<Word> words = Collections.emptyList();

        if(true == wordValueHolder.isTrExist() && true == wordValueHolder.isEnExist()){
            new ResponseEntity<List<Word>>(wordValueHolder.getIntersectedWords(), HttpStatus.NOT_IMPLEMENTED);
        }else {

            return new ResponseEntity<List<Word>>(Arrays.asList(wordRespository.save(word)), HttpStatus.OK);
        }
    }

    private Word CreateNewWord(String meaning, String example, WordValue trWordValue, WordValue enWordValue){


    }


}
