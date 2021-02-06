package com.wordmemoriser.Word;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WordResponse {

    private String trWordMeaning;

    private String enWordMeaning;

    private int id;

    private List<String> wordValues;

    private String example;
}
