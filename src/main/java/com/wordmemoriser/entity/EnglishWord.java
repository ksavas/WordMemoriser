package com.wordmemoriser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class EnglishWord {

    @Id
    @GeneratedValue
    private int Id;

    private String value;

    @ManyToMany
    private List<TurkishWord> turkishWords;

}
