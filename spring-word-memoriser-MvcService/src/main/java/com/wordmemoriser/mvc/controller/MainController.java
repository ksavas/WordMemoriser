package com.wordmemoriser.mvc.controller;

import com.wordmemoriser.mvc.model.Account;
import com.wordmemoriser.mvc.service.AccountService;
import com.wordmemoriser.mvc.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    AccountService accountService;

    @Autowired
    WordService wordService;

    @GetMapping("/")
    public String home(Model model){
        return accountService.getLoginPage(model);
    }
    @PostMapping(value = "/", params = "action=signIn")
    public String signIn(Model model, Account account){
        return accountService.signInControls(model,account);
    }
    @PostMapping(value = "/", params = "action=signUp")
    public String signUp(Model model, Account account){
        return accountService.signUpControls(model,account);
    }

    @GetMapping("/results/{id}")
    public String getResultsPage(Model model, @PathVariable Integer id){
        return wordService.setPage(model,id,"Results");
    }
    @GetMapping("/exam/{id}")
    public String getExamPage(Model model, @PathVariable Integer id){
        return wordService.setPage(model,id,"Exam");
    }
    @GetMapping("/addWord/{id}")
    public String getAddWordPage(Model model, @PathVariable Integer id) {
        return wordService.setPage(model,id,"AddWord");
    }
}
