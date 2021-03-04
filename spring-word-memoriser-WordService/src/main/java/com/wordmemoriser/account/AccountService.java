package com.wordmemoriser.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

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
                .words(new HashSet<>())
                .build();
        accountRepository.save(account);
        return HttpStatus.CREATED;
    }
}
