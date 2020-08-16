package com.wordmemoriser.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class WordMeaning {

    @Id
    @GeneratedValue
    private int Id;


    private String turkishMeaning;

    private String englishMeaning;

    private String example;

    private int point;

    @ManyToMany
    private List<WordValue> trWordValues;

    @ManyToMany
    private List<WordValue> enWordValues;

    @OneToMany
    private List<Word> words;

    public int getPoint() {
        return point;
    }

    public void addPoint(int point) {
        this.point = this.point + point;
    }


}
