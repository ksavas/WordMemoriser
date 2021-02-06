package com.wordmemoriser.WordValue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.Set;

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

    @Override
    public String toString(){
        return "{ Id: " + getId()
                + ", Value: " + getValue() + ", Language: " + getLanguage()
                + ", Turkish Meant Words size: " + getTrMeantWords().size()
                + ", English Meant Words size: " + getEnMeantWords().size() + " }";
    }

}
