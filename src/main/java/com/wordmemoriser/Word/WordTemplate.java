package com.wordmemoriser.Word;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WordTemplate {

    public String trWordValue;

    public String enWordValue;

    public String trMeaning;

    public String enMeaning;

    public String example;

    public String wordType;

    public boolean forceSave;

}
