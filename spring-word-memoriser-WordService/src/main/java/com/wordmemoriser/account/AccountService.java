package com.wordmemoriser.account;

import com.wordmemoriser.Word.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public HttpStatus mapAccount(int remoteId){
        Optional<Account> optAccount = accountRepository.findAll().stream().filter(x -> x.getRemoteId() == remoteId).findFirst();
        if(optAccount.isPresent()){
            return HttpStatus.CONFLICT;
        }
        Account account = Account.builder()
                .remoteId(remoteId)
                .accountWordPoints(new HashSet<>())
                .build();
        accountRepository.save(account);
        return HttpStatus.CREATED;
    }

    public Set<Word> getWordsByAccount(Integer remoteId, int upperLimit, int lowerLimit){
        return accountRepository
                .findAll()
                .stream()
                .filter(x -> x.getRemoteId() == remoteId)
                .findFirst()
                .get()
                .getAccountWordPoints()
                .stream()
                .map(x -> x.getWord())
                .filter(_word -> _word.getPoint() > lowerLimit && _word.getPoint() < upperLimit)
                .collect(Collectors.toSet());
    }
}
