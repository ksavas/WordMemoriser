package com.wordmemoriser.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Word {

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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
