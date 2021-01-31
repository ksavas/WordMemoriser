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

    public WordValueHolder checkWordValues(String trWordValue, String enWordValue){

        logger.log(Level.getLevel("INTERNAL"),"[SaveWordValuesIfNotExist] Entered SaveWordValuesIfNotExist with values:");
        logger.log(Level.getLevel("INTERNAL"),"- trWordValue: " + trWordValue);
        logger.log(Level.getLevel("INTERNAL"),"- enWordValue: " + enWordValue);

        String checkedTrValue = getCheckedWordValue(trWordValue);
        String checkedEnValue = getCheckedWordValue(enWordValue);
        logger.log(Level.getLevel("DEEPER"),"[SaveWordValuesIfNotExist] checkedTrValue: " + checkedTrValue + ", checkedEnValue: " + checkedEnValue);

        checkWordValue(trWordValue,"TR",checkedTrValue);
        checkWordValue(enWordValue,"EN",checkedEnValue);

        logger.log(Level.getLevel("INTERNAL"),"[SaveWordValuesIfNotExist] wordValueHolder is filled with new WordValues: " + wordValueHolder.toString());

        return wordValueHolder;
    }
    private String getCheckedWordValue(String rawWordValue){
        return rawWordValue
                .replace(" ","")
                .toLowerCase()
                .trim();
    }

    private void checkWordValue(String wordValue, String language, String checkedWordValue){

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
            logger.log(Level.getLevel("INTERNAL"),"[saveWordValueIfNotExist] The WordValue exists in the db, WordValue: " + currentWordValue.toString());
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

            logger.log(Level.getLevel("INTERNAL"),"[saveWordValueIfNotExist] The WordValue doesn't exist in the db so it's going to be stored to db, WordValue: " + currentWordValue.toString());
        }

        wordValueHolder.setWordValue(currentWordValue,isExist);
    }

    public WordValue saveWordValue(WordValue wordValue){
       return wordValueRepository.save(wordValue);
    }

    public void deleteWordValueIfChildless(WordValue wordValue){
        if(wordValue.getLanguage().equalsIgnoreCase("TR") && wordValue.getTrMeantWords().size() == 1 && wordValue.getEnMeantWords().size() == 0){
            wordValueRepository.deleteWordValueById(wordValue.getId());
            logger.log(Level.getLevel("INTERNAL"),"[deleteWordValueIfChildless] The Word Value has no other children, so it was deleted from db successfully, " +
                    "Word Value: " + wordValue.toString());
        }
        else if (wordValue.getLanguage().equalsIgnoreCase("EN") && wordValue.getEnMeantWords().size() == 1 && wordValue.getTrMeantWords().size() == 0){
            wordValueRepository.deleteWordValueById(wordValue.getId());
            logger.log(Level.getLevel("INTERNAL"),"[deleteWordValueIfChildless] The Word Value has no other children, so it was deleted from db successfully, " +
                    "Word Value: " + wordValue.toString());
        }
        else {
            logger.warn("[deleteWordValueIfChildless] The Word Value has some other words so it couldn't be deleted from db, Word Value: " + wordValue.toString());
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
