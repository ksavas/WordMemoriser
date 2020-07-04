package com.wordmemoriser.dto;


import com.wordmemoriser.entity.TurkishWord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TurkishWordRequest {

    private TurkishWord turkishWord;


    public TurkishWord getTurkishWord() {
        return turkishWord;
    }

    public void setTurkishWord(TurkishWord turkishWord) {
        this.turkishWord = turkishWord;
    }
}
