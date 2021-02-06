package com.wordmemoriser.WordValue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface WordValueRepository extends JpaRepository<WordValue,Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM word_values where Id = :id", nativeQuery = true)
    void deleteWordValueById(Integer id);

}
