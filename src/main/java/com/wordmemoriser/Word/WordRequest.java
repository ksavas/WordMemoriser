package com.wordmemoriser.Word;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WordRequest {

    private WordTemplate wordTemplate;

    public WordTemplate getWordTemplate() {
        return wordTemplate;
    }

    public void setWordTemplate(WordTemplate WordTemplate) {
        this.wordTemplate = WordTemplate;
    }
}
