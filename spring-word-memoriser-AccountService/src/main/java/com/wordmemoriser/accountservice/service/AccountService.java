package com.wordmemoriser.accountservice.service;

import com.wordmemoriser.accountservice.dao.AccountRepository;
import com.wordmemoriser.accountservice.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public ResponseEntity<Account> signInControls(String nick, String password){
        Optional<Account> optAccount = accountRepository.findAll().stream().filter(x -> x.getNick().equals(nick)).findFirst();

        if(optAccount.isPresent()){
            Account account = optAccount.get();
            if(account.getPassword().equals(password)){
                return new ResponseEntity<>(optAccount.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Account> signUpControls(String nick, String password){
        Optional<Account> optAccount = accountRepository.findAll().stream().filter(x -> x.getNick().equals(nick)).findFirst();
        if(optAccount.isPresent()){
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
        else {
            Account account = Account.builder()
                    .nick(nick)
                    .password(password)
                    .build();
            accountRepository.save(account);
            return new ResponseEntity<>(account,HttpStatus.CREATED);
        }
    }

}
