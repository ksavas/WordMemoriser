package com.wordmemoriser.Word;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Exam.ExamType;
import com.wordmemoriser.WordMeaning.WordMeaning;
import com.wordmemoriser.WordValue.WordValue;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "words")
@Getter
@Setter
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int Id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "word_meaning_id")
    @JsonIgnore
    private WordMeaning wordMeaning;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "tr_word_value_id")
    @JsonIgnore
    private WordValue trWordValue;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "en_word_value_id")
    @JsonIgnore
    private WordValue enWordValue;

    @Getter
    @Setter
    private int point;

    @Getter
    @Setter
    private String wordType;


    public String getValue(ExamType examType, Language language){
        if(examType.equals(ExamType.WORD) && language.equals(Language.TR)){
            return trWordValue.getValue();
        }
        else if(examType.equals(ExamType.WORD) && language.equals(Language.EN)){
            return enWordValue.getValue();
        }
        else if(examType.equals(ExamType.MEANING) && language.equals(Language.TR)){
            return wordMeaning.getTurkishMeaning();
        }
        else if(examType.equals(ExamType.MEANING) && language.equals(Language.EN)){
            return wordMeaning.getEnglishMeaning();
        }
        else{
            return null;
        }
    }

    public void setPoint(Integer point){
        this.point += point;
    }
}
