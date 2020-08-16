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

    private boolean trWordValueExist;

    private WordValue enWordValue;

    private boolean enWordValueExist;


    public void setWordValue(WordValue wordValue, boolean wordValueExist){
        if(wordValue.getLanguage().equals("TR")){
            this.trWordValue=wordValue;
            this.trWordValueExist=wordValueExist;
        }
        else {
            this.enWordValue=wordValue;
            this.enWordValueExist=wordValueExist;
        }

    }
}
