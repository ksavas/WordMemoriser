package com.wordmemoriser.WordMeaning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.util.Set;

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
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
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
