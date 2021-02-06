package com.wordmemoriser.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordmemoriser.Word.Word;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Users")
@Getter
@Setter
@Table(name = "Users")
public class User {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int Id;

    @Getter
    @Setter
    private int relatedId;

    @Getter
    @Setter
    private String userName;


    @OneToMany(
            targetEntity = com.wordmemoriser.User.UserWordPoint.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "user"
    )
    @Getter
    @Setter
    @ToString.Exclude
    @JsonIgnore
    private Set<UserWordPoint> userWordPoints;

    public void addUserWordPoint(UserWordPoint userWordPoint){
        this.userWordPoints.add(userWordPoint);
    }
}
