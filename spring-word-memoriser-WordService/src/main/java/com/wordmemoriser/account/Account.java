package com.wordmemoriser.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "accounts")
@Getter
@Setter
@Table(name = "accounts")
public class Account {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int Id;

    @Getter
    @Setter
    private int remoteId;


    @OneToMany(
            targetEntity = com.wordmemoriser.Word.Word.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "account"
    )
    @Getter
    @Setter
    @ToString.Exclude
    @JsonIgnore
    private Set<Word> words;

}
