package com.wordmemoriser.WordValue;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class WordValueHolder {

    private WordValue trWordValue;

    private boolean trWordValueExisted;

    private WordValue enWordValue;

    private boolean enWordValueExisted;


    public void setWordValue(WordValue wordValue, boolean wordValueExists){
        if(wordValue.getLanguage().equals("TR")){
            this.trWordValue=wordValue;
            this.trWordValueExisted=wordValueExists;
        }
        else {
            this.enWordValue=wordValue;
            this.enWordValueExisted=wordValueExists;
        }
    }
}
