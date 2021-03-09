package com.wordmemoriser.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/mapAccount/{remoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus signUp(@PathVariable Integer remoteId){
        return accountService.mapAccount(remoteId);
    }
}
