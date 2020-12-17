package com.wordmemoriser.Word;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WordRequestTemplate extends WordTemplate {

    public boolean forceSave;

}
