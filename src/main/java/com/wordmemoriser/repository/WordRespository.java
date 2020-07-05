package com.wordmemoriser.repository;

import com.wordmemoriser.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRespository extends JpaRepository<Word,Integer> {


}
