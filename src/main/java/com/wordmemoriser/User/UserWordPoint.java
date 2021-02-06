package com.wordmemoriser.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "UserWordPoint")
@Getter
@Setter
@Table(name = "UserWordPoint")
public class UserWordPoint {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int Id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "Id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @Getter
    @Setter
    private User user;

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
