package com.wordmemoriser.controller;


import com.wordmemoriser.dto.WordRequest;
import com.wordmemoriser.model.Word;
import com.wordmemoriser.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordController {


    @Autowired
    private WordService wordService ;

    @PostMapping("/saveWord")
    public ResponseEntity<List<Word>> saveWord(@RequestBody WordRequest wordRequest){
        return  wordService.saveWordIfNotExist(wordRequest.getWordTemplate());
    }



}
