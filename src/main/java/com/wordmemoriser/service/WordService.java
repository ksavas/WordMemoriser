package com.wordmemoriser.service;

import com.wordmemoriser.entity.Word;
import com.wordmemoriser.repository.WordRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {

    @Autowired
    private WordRespository wordRespository;


    public ResponseEntity<List<Word>> saveWordIfNotExist(Word word){
        List<Word> words = wordRespository.findAll().stream().filter(currentWord -> word.getValue().equalsIgnoreCase(currentWord.getValue())).collect(Collectors.toList());
        if(words.size()==0){
            return new ResponseEntity<List<Word>>(Arrays.asList(wordRespository.save(word)), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<Word>>(words, HttpStatus.NOT_IMPLEMENTED);
        }
    }

}
