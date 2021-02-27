package com.wordmemoriser.mvc.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.wordmemoriser.mvc.model.Account;
import com.wordmemoriser.mvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    AccountService accountService;


    @GetMapping("/")
    public String home(Model model){
                Account account = new Account();
        model.addAttribute("account",account);
        model.addAttribute("testBoolT",true);
        model.addAttribute("testBoolF",false);
        return "login";
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

    @GetMapping("/signup")
    public String signUp(Model model){
        return "asd";
    }

    @PostMapping("/")
    public String greetingSubmit(Model model, Account account) {
        accountService.signInControls(account);
        model.addAttribute("account", account);
        return "AddWord";
    }
}
