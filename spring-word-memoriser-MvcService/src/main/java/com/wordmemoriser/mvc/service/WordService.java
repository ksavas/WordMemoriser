package com.wordmemoriser.mvc.service;

import com.wordmemoriser.mvc.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class WordService {

    public String setPage(Model model, Integer id, String templateName){
        Account account = new Account();
        account.setId(id);
        model.addAttribute("account",account);
        return templateName;
    }
}
