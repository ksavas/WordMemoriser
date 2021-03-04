package com.wordmemoriser.mvc.service;

import com.wordmemoriser.mvc.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {

    private static final String GATEWAY_URI = "http://localhost:8662/";
    private static final String accountSignInRoute = "account/signIn";
    private static final String accountSignUpRoute = "account/signUp";

    public String getLoginPage(Model model){
        model.addAttribute("account",new Account());
        return "login";
    }

    public String signInControls(Model model, Account account) {
        String uri =  GATEWAY_URI + accountSignInRoute;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Account> result = null;

        try{
            result = restTemplate.postForEntity(uri, account,Account.class);
        }catch (HttpClientErrorException e){
            int statusCode = e.getRawStatusCode();
            if(statusCode == HttpStatus.FORBIDDEN.value()){
                model.addAttribute("accountReturnState","Wrong Password !!");
            }
            else if (statusCode ==HttpStatus.NOT_FOUND.value()){
                model.addAttribute("accountReturnState","Account Not Found !!");
            }
            else {
                model.addAttribute("accountReturnState","An internal error occured, please try again..");
            }
            return "login";
        }
        if(result.getStatusCode().equals(HttpStatus.OK)){
            model.addAttribute("account",result.getBody());
            return "AddWord";
        }
        return "login";
    }
    public String signUpControls(Model model, Account account){
        String uri =  GATEWAY_URI + accountSignUpRoute;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Account> result = null;

        try{
            result = restTemplate.postForEntity(uri,account,Account.class);
        }catch (HttpClientErrorException e){
            int statusCode = e.getRawStatusCode();
            if(statusCode == HttpStatus.CONFLICT.value()){
                model.addAttribute("accountReturnState","The nick exists: " + account.getNick());
            }
            else {
                model.addAttribute("accountReturnState","An internal error occured, please try again..");
            }
            return "login";
        }
        if(result.getStatusCode().equals(HttpStatus.CREATED)){
            model.addAttribute("account",result.getBody());
            return "AddWord";
        }
        return "login";
    }
}
