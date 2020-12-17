package com.wordmemoriser.Word;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WordPointTemplate {

    public int id;

    public String trWordValue;

    public String enWordValue;

    public String trMeaning;

    public String enMeaning;

    public String wordType;

    public int point;

}
