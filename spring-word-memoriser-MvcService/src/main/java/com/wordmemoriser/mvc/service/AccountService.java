package com.wordmemoriser.mvc.service;

import com.wordmemoriser.mvc.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {

    public void signInControls(Account account){
        final String uri = "http://localhost:9093/signIn/?nick="+account.getNick()+"&password="+account.getPassword();
        RestTemplate restTemplate = new RestTemplate();
        //account = restTemplate.getForObject(uri, ResponseEntity<Account>.class);

    }

}
