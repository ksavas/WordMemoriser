package com.wordmemoriser.accountservice.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

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
    private String nick;

    @Getter
    @Setter
    private String password;
}
