package com.wordmemoriser.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class TurkishWord {


    @Id
    @GeneratedValue
    private int id;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    private String wordClass;

    private int Point;



    @ManyToMany
    private List<EnglishWord> englishWords;

}
