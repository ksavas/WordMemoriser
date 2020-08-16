package com.wordmemoriser.Word;


import com.wordmemoriser.WordMeaning.WordMeaning;
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
    public ResponseEntity<List<WordMeaning>> saveWord(@RequestBody WordRequest wordRequest){
        return  wordService.saveWordIfNotExist(wordRequest.getWordTemplate());
    }

}
