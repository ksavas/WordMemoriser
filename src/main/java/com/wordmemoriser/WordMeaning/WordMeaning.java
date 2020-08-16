package com.wordmemoriser.WordMeaning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import com.wordmemoriser.WordValue.WordValue;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wordmeanings")
@Builder
@Getter
@Setter
public class WordMeaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Getter
    @Setter
    private String turkishMeaning;

    @Getter
    @Setter
    private String englishMeaning;

    @Getter
    @Setter
    private String example;

    @Getter
    @Setter
    private String checkedTurkishMeaning;

    @Getter
    @Setter
    private String checkedEnglishMeaning;

    @Getter
    @Setter
    private String checkedExample;

    @OneToMany(
            targetEntity = com.wordmemoriser.Word.Word.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "wordMeaning"
    )
    @Getter
    @Setter
    @ToString.Exclude
    @JsonIgnore
    private Set<Word> words;

    public void addWord(Word word){
        this.words.add(word);
    }

}
