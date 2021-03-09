package com.wordmemoriser.Exam;

import com.wordmemoriser.Word.WordRespository;
import com.wordmemoriser.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
public class ExamController {

    @Autowired
    ExamService examService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WordRespository wordRespository;

    @PostMapping("/exam")
    public ResponseEntity<List<WordQuestion>> getExam(@RequestBody ExamRequestTemplate examRequestTemplate){
        return examService.getQuestionWords(examRequestTemplate);
    }

    @PutMapping("/exam")
    public HttpStatus updateWordPoint(@RequestParam Integer remoteId,@RequestParam Integer wordId,@RequestParam Integer point){
        return examService.updateWordPoint(remoteId,wordId, point);
    }

}
