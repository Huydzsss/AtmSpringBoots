package org.example.atm.Controller;

import org.example.atm.Model.Account;
import org.example.atm.Service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller

public class AuthController {
    private final AccountService accountService;
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/")
    public String showLoginForm() {
        return "Login";
    }
    @PostMapping("/Login")
    public String login(@RequestParam String accountNumber, Model model) {
        Optional<Account> account = accountService.getAccountByNumber(accountNumber);
        if (account.isPresent()) {
            model.addAttribute("account", account.get());
            return "dashboard";
        } else {
            model.addAttribute("error", "Tài khoản không tồn tại!");
            return "Login";
        }
    }
}
