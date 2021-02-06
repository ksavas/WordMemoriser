package com.wordmemoriser.WordMeaning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface WordMeaningRepository extends JpaRepository<WordMeaning,Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wordMeanings where Id = :id", nativeQuery = true)
    void deleteWordMeaningById(Integer id);

}
