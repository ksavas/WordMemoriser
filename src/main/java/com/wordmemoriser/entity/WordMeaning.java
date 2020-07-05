package com.wordmemoriser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class WordMeaning {

    @Id
    @GeneratedValue
    private int Id;

    private String englishMeaning;

    private String turkishMeaning;

    private String example;

    @OneToMany
    private List<Word> meaningWords;


}
