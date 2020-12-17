package com.wordmemoriser.WordMeaning;

import com.wordmemoriser.Word.WordRequestTemplate;
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

        return Math.min(trCount, enCount);
    }
}
