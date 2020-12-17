package com.wordmemoriser.Exam;

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

    @PostMapping("/exam")
    public ResponseEntity<List<WordQuestion>> getExam(@RequestBody ExamRequestTemplate examRequestTemplate){
        return examService.getQuestionWords(examRequestTemplate);
    }

    @PutMapping("/exam")
    public HttpStatus updateWordPoint(@RequestParam Integer wordId, Integer point){
        return examService.updateWordPoint(wordId, point);
    }

}
