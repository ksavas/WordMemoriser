package com.wordmemoriser.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "AccountWordPoint")
@Getter
@Setter
@Table(name = "AccountWordPoint")
public class AccountWordPoint {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int Id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "Id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @Getter
    @Setter
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id", referencedColumnName = "Id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @Getter
    @Setter
    private Word word;

    @Getter
    @Setter
    private int point;
}
