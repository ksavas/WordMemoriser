package com.wordmemoriser.WordValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordValueService {

    @Autowired
    private WordValueRepository wordValueRepository;

    @Autowired
    private WordValueHolder wordValueHolder;

    public WordValueHolder SaveWordValuesIfNotExist(String trWordValue, String enWordValue){
        String checkedTrValue = trWordValue
                .replace(" ","")
                .toLowerCase();

        String checkedEnValue = enWordValue
                .replace(" ","")
                .toLowerCase();

        saveWordValueIfNotExist(trWordValue,"TR",checkedTrValue);
        saveWordValueIfNotExist(enWordValue,"EN",checkedEnValue);

        return wordValueHolder;
    }

    private void saveWordValueIfNotExist(String wordValue, String language, String checkedWordValue){

        WordValue currentWordValue;
        boolean isExist;

        Optional<WordValue> optionalWordValue = wordValueRepository
                .findAll()
                .stream()
                .filter(_wordValue -> _wordValue.getCheckedWordValue().equalsIgnoreCase(checkedWordValue) && _wordValue.getLanguage().equalsIgnoreCase(language))
                .findFirst();

        if(optionalWordValue.isPresent()){
            currentWordValue = optionalWordValue.get();
            isExist = true;
        }
        else {
            currentWordValue = WordValue
                    .builder()
                    .value(wordValue)
                    .language(language)
                    .checkedWordValue(checkedWordValue)
                    .trMeantWords(new HashSet<>())
                    .enMeantWords(new HashSet<>())
                    .build();

            isExist=false;

            wordValueRepository.save(currentWordValue);
        }

        wordValueHolder.setWordValue(currentWordValue,isExist);
    }

    public List<WordValue> getAllWordValues(){
        return wordValueRepository.findAll();
    }
}
