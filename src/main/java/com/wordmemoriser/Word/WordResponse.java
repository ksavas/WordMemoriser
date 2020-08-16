package com.wordmemoriser.Word;

import com.wordmemoriser.WordValue.WordValue;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WordResponse {

    private String trWordMeaning;

    private String enWordMeaning;

    private int id;

    private List<String> wordValues;

    public void  addWordValues(String wordValue){
        this.wordValues.add(wordValue);
    }

    private String example;
}
