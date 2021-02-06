package com.wordmemoriser.Word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface WordRespository extends JpaRepository<Word,Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM words where Id = :id", nativeQuery = true)
    void deleteWordById(Integer id);

}
