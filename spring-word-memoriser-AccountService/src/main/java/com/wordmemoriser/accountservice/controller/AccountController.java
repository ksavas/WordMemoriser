package com.wordmemoriser.accountservice.controller;

import com.wordmemoriser.accountservice.model.Account;
import com.wordmemoriser.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping(value = "/signIn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> login(@RequestBody Account account){
        return accountService.signInControls(account.getNick(),account.getPassword());
    }

    @PostMapping(value = "/signUp" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> signUp(@RequestBody Account account){
        return accountService.signUpControls(account.getNick(),account.getPassword());
    }
}
