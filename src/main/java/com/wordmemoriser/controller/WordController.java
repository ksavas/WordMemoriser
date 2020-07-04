package com.wordmemoriser.controller;


import com.wordmemoriser.dto.TurkishWordRequest;
import com.wordmemoriser.entity.TurkishWord;
import com.wordmemoriser.service.TurkishWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordController {


    @Autowired
    private TurkishWordService turkishWordService;


    @PostMapping("/saveWord")
    public TurkishWord saveWord(@RequestBody TurkishWordRequest turkishWordRequest){
        return  turkishWordService.saveWordIfNotExist(turkishWordRequest.getTurkishWord());
    }
    @GetMapping("/findAllWords")
    public List<TurkishWord> getAllWords(){
        return  turkishWordService.getAllWords();
    }

}
