package com.wordmemoriser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Word {

    @Id
    @GeneratedValue
    private int Id;

    @ManyToOne
    private WordMeaning wordMeaning;

    @ManyToOne
    private WordValue wordValue;

    public String getValue(){
        return wordValue.getValue();
    }

    private int point;

    public int getPoint() {
        return point;
    }

    public void addPoint(int point) {
        this.point = this.point + point;
    }

}
