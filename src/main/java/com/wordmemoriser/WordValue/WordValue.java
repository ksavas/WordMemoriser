package com.wordmemoriser.WordValue;
import ch.qos.logback.classic.db.names.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Builder
@Entity(name = "word_values")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WordValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int Id;

    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    private String language;

    @Getter
    @Setter
    private String checkedWordValue;

    @OneToMany(
            targetEntity = com.wordmemoriser.Word.Word.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "trWordValue"
    )
    @Getter
    @Setter
    @ToString.Exclude
    @JsonIgnore
    private Set<Word> trMeantWords;

    @OneToMany(
            targetEntity = com.wordmemoriser.Word.Word.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "enWordValue"
    )
    @Getter
    @Setter
    @ToString.Exclude
    @JsonIgnore
    private Set<Word> enMeantWords;

    public void addTrMeantWord(Word word){
        this.trMeantWords.add(word);
    }
    public void addEnMeantWord(Word word){
        this.enMeantWords.add(word);
    }

}
