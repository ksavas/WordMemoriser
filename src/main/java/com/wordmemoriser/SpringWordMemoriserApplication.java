package com.wordmemoriser;


import com.wordmemoriser.WordMeaning.WordMeaningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.stream.Collectors;


@SpringBootApplication
@EntityScan
public class SpringWordMemoriserApplication {

    @Autowired
    static WordMeaningRepository wordMeaningRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringWordMemoriserApplication.class, args);

    }
}
