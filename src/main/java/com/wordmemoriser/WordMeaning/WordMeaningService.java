package com.wordmemoriser.WordMeaning;

import com.wordmemoriser.Word.Word;
import com.wordmemoriser.Word.WordRespository;
import com.wordmemoriser.Word.WordTemplate;
import com.wordmemoriser.WordValue.WordValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordMeaningService {

    @Autowired
    WordMeaningRepository wordMeaningRepository;

    public WordMeaningHolder saveWordMeaning(WordTemplate wordTemplate){

        String checkedTurkishMeaning = wordTemplate.trMeaning
                .replace(" ","")
                .toLowerCase();

        String checkedEnglishMeaning = wordTemplate.enMeaning
                .replace(" ","")
                .toLowerCase();

        String checkedExample = wordTemplate.example
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
                    .turkishMeaning(wordTemplate.trMeaning)
                    .englishMeaning(wordTemplate.enMeaning)
                    .example(wordTemplate.example)
                    .checkedTurkishMeaning(checkedTurkishMeaning)
                    .checkedEnglishMeaning(checkedEnglishMeaning)
                    .checkedExample(checkedExample)
                    .words(new HashSet<>())
                    .build();

            wordMeaningRepository.save(newWordMeaning);
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

    public List<WordMeaning> findWordMeaningsByWords(List<Word> words){

        Set<Integer> wordMeaningIds = words
                .stream()
                .map(x -> x.getWordMeaning().getId())
                .collect(Collectors.toSet());

        return wordMeaningRepository.findAllById(wordMeaningIds);
    }
}
