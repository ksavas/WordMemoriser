package com.wordmemoriser.accountservice.service;

import com.wordmemoriser.accountservice.dao.AccountRepository;
import com.wordmemoriser.accountservice.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountService {

    private static final String GATEWAY_URI = "http://localhost:8662/";
    private static final String wordServiceMapAccountRoute = "word/mapAccount/";

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

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<HttpStatus> result = null;

            String uri = GATEWAY_URI + wordServiceMapAccountRoute + account.getId();

            try{
                result = restTemplate.getForEntity(uri,HttpStatus.class);
            }catch (HttpClientErrorException e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(result.getBody().equals(HttpStatus.CONFLICT)){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(account,HttpStatus.CREATED);
        }
    }

}
