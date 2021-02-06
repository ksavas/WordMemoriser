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

    public ResponseEntity<Account> checkAccount(String nick, String password){
        Optional<Account> account = accountRepository.findAll().stream().filter(x -> x.getNick().equals(nick) && x.getPassword().equals(password)).findFirst();
        if(account.isPresent()){
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

}
