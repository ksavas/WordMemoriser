package com.wordmemoriser.Word;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WordTemplate {

    public Integer id;

    public String trWordValue;

    public String enWordValue;

    public String trMeaning;

    public String enMeaning;

    public String example;

    public String wordType;

    public Integer point;

    public Boolean forceSave;

    public Boolean isForceSave(){
        return forceSave;
    }

    @Override
    public String toString(){
        return "{ Id: " + id + ", trWordValue: " + trWordValue + ", enWordValue: " + enWordValue + ", trMeaning: " + trMeaning
                + ", enMeaning: " + enMeaning + ", example: " + example + ", wordType: " + wordType + ", point: " + point
                + ", forceSave: " + forceSave;
    }

}
