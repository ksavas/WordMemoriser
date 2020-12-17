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

    public String trWordValue;

    public String enWordValue;

    public String trMeaning;

    public String enMeaning;

    public String example;

    public String wordType;

}
