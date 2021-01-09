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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    static Logger logger = LogManager.getLogger(Word.class);

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
        int oldVal = point;
        this.point += point;
        logger.log(Level.getLevel("INTERNAL"),"[setPoint] The word point is updated to: " + this.point + ", old Point: " + oldVal + ", added value: " + point);
    }

    public String toString(){
        return "{ Id: " + getId() + ", Tr Word Value: " + trWordValue.getValue() + ", En Word Value: " + enWordValue.getValue()
                + ", Word Type: " + wordType + ", Turkish Word Meaning: " + wordMeaning.getTurkishMeaning()
                + ", English Word Meaning: " + wordMeaning.getEnglishMeaning() + ", Point: " + point + " }";
    }

}
