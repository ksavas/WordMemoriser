package com.wordmemoriser.WordMeaning;

import com.wordmemoriser.Word.WordRequestTemplate;
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
public class WordMeaningService {

    @Autowired
    WordMeaningRepository wordMeaningRepository;

    Logger logger = LogManager.getLogger(WordMeaningService.class);

    private List<WordMeaning> currentWordMeanings;

    public WordMeaningHolder checkWordMeaning(WordRequestTemplate wordRequestTemplate){

        String checkedTurkishMeaning = wordRequestTemplate.trMeaning
                .replace(" ","")
                .toLowerCase();

        String checkedEnglishMeaning = wordRequestTemplate.enMeaning
                .replace(" ","")
                .toLowerCase();

        String checkedExample = wordRequestTemplate.example
                .replace(" ","")
                .toLowerCase();

        Optional<WordMeaning> optionalWordMeaning = wordMeaningRepository
                .findAll()
                .stream()
                .filter(x ->
                        x.getCheckedTurkishMeaning().equalsIgnoreCase(checkedTurkishMeaning) &&
                        x.getCheckedEnglishMeaning().equalsIgnoreCase(checkedEnglishMeaning) &&
                        x.getCheckedExample().equalsIgnoreCase(checkedExample))
                .findFirst();

        if(!optionalWordMeaning.isPresent()){
            WordMeaning newWordMeaning = WordMeaning.builder()
                    .turkishMeaning(wordRequestTemplate.trMeaning)
                    .englishMeaning(wordRequestTemplate.enMeaning)
                    .example(wordRequestTemplate.example)
                    .checkedTurkishMeaning(checkedTurkishMeaning)
                    .checkedEnglishMeaning(checkedEnglishMeaning)
                    .checkedExample(checkedExample)
                    .words(new HashSet<>())
                    .build();

            WordMeaningHolder wordMeaningHolder = WordMeaningHolder
                    .builder()
                    .wordMeaning(newWordMeaning)
                    .isExist(false)
                    .build();
            return wordMeaningHolder;
        }
        else {
            return WordMeaningHolder
                    .builder()
                    .wordMeaning(optionalWordMeaning.get())
                    .isExist(true)
                    .build();
        }
    }

    public WordMeaning saveWordMeaning(WordMeaning wordMeaning){
        return wordMeaningRepository.save(wordMeaning);
    }

    public void deleteWordMeaningIfChildless(WordMeaning wordMeaning){
        if(wordMeaning.getWords().size() == 1){
            wordMeaningRepository.deleteWordMeaningById(wordMeaning.getId());
        }
    }

    public void setRepository(){
        currentWordMeanings = new ArrayList<>(wordMeaningRepository.findAll());
        logger.log(Level.getLevel("INTERNAL"),"[setRepository] " + "Current WordMeanings are retrieved from db, size: " + currentWordMeanings.size());
        logger.log(Level.getLevel("DEEPER"),"[setRepository] Current WordMeanings: " + currentWordMeanings.toString());
    }

    public Integer getMinWordMeaningCount(){
        int trCount = (int) currentWordMeanings
                .stream()
                .map(x -> x.getTurkishMeaning())
                .count();

        int enCount = (int) currentWordMeanings
                .stream()
                .map(x -> x.getEnglishMeaning())
                .count();

        logger.log(Level.getLevel("INTERNAL"),"[getMinWordMeaningCount] " + " Current Turkish WordMeaning count: " + trCount + ", Current English WordMeaning count: " + enCount);

        return Math.min(trCount, enCount);
    }
}
