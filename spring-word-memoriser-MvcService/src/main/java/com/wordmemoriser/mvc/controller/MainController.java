package com.wordmemoriser.mvc.controller;

import com.wordmemoriser.mvc.model.Account;
import com.wordmemoriser.mvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    AccountService accountService;

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

    @GetMapping("/results")
    public String getResultsPage(Model model){
        return "Results";
    }
    @GetMapping("/exam")
    public String getExamPage(Model model){
        return "Exam";
    }
    @GetMapping("/addWord")
    public String getAddWordPage(Model model)
    {
        Account account = new Account();
        model.addAttribute("account",account);
        return "AddWord";
    }
}
