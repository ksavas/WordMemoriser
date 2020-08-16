package com.wordmemoriser.Word;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.WordMeaning.WordMeaning;
import com.wordmemoriser.WordValue.WordValue;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.*;

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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "word_meaning_id")
    @JsonIgnore
    private WordMeaning wordMeaning;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
    private int enAskedPoint;

    @Getter
    @Setter
    private int trAskedPoint;

    @Getter
    @Setter
    private String wordType;
}
