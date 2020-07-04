package com.wordmemoriser.repository;

import com.wordmemoriser.entity.TurkishWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurkishWordRespository extends JpaRepository<TurkishWord,Integer> {


}
