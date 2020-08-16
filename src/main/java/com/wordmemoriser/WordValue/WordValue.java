package com.wordmemoriser.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor()
@AllArgsConstructor()
@Setter
@Getter
@ToString
@Builder
@Entity
public class WordValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    private String value;

    private String language;

    @OneToMany
    private List<Word> words;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

}
