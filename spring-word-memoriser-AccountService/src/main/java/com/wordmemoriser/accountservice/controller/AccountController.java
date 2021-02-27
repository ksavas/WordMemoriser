package com.wordmemoriser.accountservice.controller;

import com.wordmemoriser.accountservice.model.Account;
import com.wordmemoriser.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/signIn")
    public ResponseEntity<Account> signIn(@RequestParam String nick, @RequestParam String password){
        return accountService.signInControls(nick,password);
    }

    @GetMapping("/SignUp")
    public ResponseEntity<Account> signUp(@RequestParam String nick, @RequestParam String password){
        return accountService.signInControls(nick,password);
    }

}
