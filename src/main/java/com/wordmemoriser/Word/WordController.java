package com.wordmemoriser.Word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
public class WordController {

    @Autowired
    private WordService wordService ;

    @PostMapping("/word")
    public ResponseEntity<List<WordTemplate>> saveWord(@RequestBody WordRequestTemplate wordRequestTemplate){
        return  wordService.saveWordIfNotExist(wordRequestTemplate);
    }

    @GetMapping("/word")
    public ResponseEntity<List<WordPointTemplate>> getAllWords(){
        return  wordService.getAllWords();
    }

    @DeleteMapping("/word")
    public ResponseEntity<List<WordTemplate>> deleteWord(@RequestParam String id){
        return wordService.deleteWord(id);
    }
}
