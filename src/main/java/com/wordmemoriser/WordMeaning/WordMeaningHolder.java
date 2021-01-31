package com.wordmemoriser.WordMeaning;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WordMeaningHolder {

    private WordMeaning wordMeaning;

    private Boolean isExist;

    @Override
    public String toString(){
        return "[ " + wordMeaning.toString() + ", isExist: " + isExist + " ]";
    }

}
