package com.wordmemoriser.service;

import com.wordmemoriser.entity.TurkishWord;
import com.wordmemoriser.repository.TurkishWordRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurkishWordService {

    @Autowired
    private TurkishWordRespository turkishWordRespository;


    public TurkishWord saveWordIfNotExist(TurkishWord turkishWord){
        if((turkishWordRespository.findAll().stream().filter(currentWord -> turkishWord.getValue().equalsIgnoreCase(currentWord.getValue())).count()==0)){
            return turkishWordRespository.save(turkishWord);
        }
        return null;
    }

    public List<TurkishWord> getAllWords(){
        return  turkishWordRespository.findAll();
    }

}
