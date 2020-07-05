package com.wordmemoriser.dto;


import com.wordmemoriser.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WordRequest {

    private Word word;

    public Word getWord() {
        return word;
    }

    public void setWord(Word Word) {
        this.word = word;
    }
}
