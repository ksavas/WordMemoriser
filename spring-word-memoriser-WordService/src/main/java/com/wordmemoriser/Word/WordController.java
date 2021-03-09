package com.wordmemoriser.Word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordController {

    @Autowired
    private WordService wordService ;

    @PostMapping("/")
    public ResponseEntity<List<WordTemplate>> saveWord(@RequestBody WordTemplate wordRequestTemplate){
        return  wordService.saveWordIfNotExist(wordRequestTemplate);
    }

    @GetMapping("/{remoteId}")
    public ResponseEntity<List<WordTemplate>> getAllWords(@PathVariable Integer remoteId){
        return  wordService.getAllWords(remoteId);
    }

    @DeleteMapping("/{remoteId}")
    public ResponseEntity<List<WordTemplate>> deleteWord(@RequestParam String id, @PathVariable Integer remoteId){
        return wordService.deleteWord(id,remoteId);
    }
}
