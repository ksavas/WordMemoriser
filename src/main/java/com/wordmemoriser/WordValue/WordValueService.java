package com.wordmemoriser.WordValue;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.ArrayList;

@Service
public class WordValueService {

    Logger logger = LogManager.getLogger(WordValueService.class);

    @Autowired
    private WordValueRepository wordValueRepository;

    @Autowired
    private WordValueHolder wordValueHolder;

    List<WordValue> currentWordValues;

    public WordValueHolder SaveWordValuesIfNotExist(String trWordValue, String enWordValue){
        String checkedTrValue = getCheckedWordValue(trWordValue);
        String checkedEnValue = getCheckedWordValue(enWordValue);

        saveWordValueIfNotExist(trWordValue,"TR",checkedTrValue);
        saveWordValueIfNotExist(enWordValue,"EN",checkedEnValue);

        return wordValueHolder;
    }

    private String getCheckedWordValue(String rawWordValue){
        return rawWordValue
                .replace(" ","")
                .toLowerCase()
                .trim();
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

    public void deleteWordValueIfChildless(WordValue wordValue){
        if(wordValue.getLanguage().equalsIgnoreCase("TR") && wordValue.getTrMeantWords().size() == 1 && wordValue.getEnMeantWords().size() == 0){
            wordValueRepository.deleteWordValueById(wordValue.getId());
        }
        else if (wordValue.getLanguage().equalsIgnoreCase("EN") && wordValue.getEnMeantWords().size() == 1 && wordValue.getTrMeantWords().size() == 0){
            wordValueRepository.deleteWordValueById(wordValue.getId());
        }
    }

    public void setRepository(){
        currentWordValues = new ArrayList<>(wordValueRepository.findAll());
        logger.log(Level.getLevel("INTERNAL"),"[setRepository] " + "Current WordValues are retrieved from db, size: " + currentWordValues.size());
        logger.log(Level.getLevel("DEEPER"),"[setRepository] Current WordValues: " + currentWordValues.toString());
    }

    public Integer getMinWordValueCount(){
        int trCount = (int) currentWordValues
                .stream()
                .filter(x -> x.getLanguage().equals("TR"))
                .map(x -> x.getValue())
                .count();

        int enCount =  (int) currentWordValues
                .stream()
                .filter(x -> x.getLanguage().equals("EN"))
                .map(x -> x.getValue())
                .count();

        logger.log(Level.getLevel("INTERNAL"),"[getMinWordValueCount] " + " Current Turkish WordValue count: " + trCount + ", Current English WordValue count: " + enCount);

        return Math.min(trCount,enCount);
    }
}
