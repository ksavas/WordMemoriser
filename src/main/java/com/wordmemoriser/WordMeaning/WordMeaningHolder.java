package com.wordmemoriser.WordMeaning;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WordMeaningHolder {

    private WordMeaning wordMeaning;

    private Boolean isExist;

}
