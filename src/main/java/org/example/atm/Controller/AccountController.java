package org.example.atm.Controller;

import lombok.RequiredArgsConstructor;
import org.example.atm.Model.Account;
import org.example.atm.Service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/")
    public String viewAccount(Model model) {
        model.addAttribute("dashboard", accountService.getAllAccounts());
        return "dashboard";
    }

    @GetMapping("/balance/{accountNumber}")
    public String checkBalance(@PathVariable String accountNumber, Model model) {
        Optional<Account> accountOpt = accountService.getAccountByNumber(accountNumber);

        if (accountOpt.isEmpty()) {
            model.addAttribute("error", "Tài khoản không tồn tại!");
            return "error"; // Trang báo lỗi
        }

        model.addAttribute("account", accountOpt.get());
        return "balance";
    }

    @GetMapping("/search/{name}")
    public String searchAccount(@PathVariable String name, Model model) {
        model.addAttribute("accounts", accountService.getAccountByNumber(name));
        return "dashboard";
    }
}
