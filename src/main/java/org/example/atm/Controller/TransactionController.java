package org.example.atm.Controller;

import org.example.atm.Model.Account;
import org.example.atm.Model.Transaction;
import org.example.atm.Service.AccountService;
import org.example.atm.Service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}")
    public String viewTransactionHistory(@PathVariable String accountNumber, Model model) {
        Optional<Account> account = accountService.getAccountByNumber(accountNumber);
        if (account.isPresent()) {
            List<Transaction> transactions = transactionService.getTransactionsByAccount(account.get());
            model.addAttribute("transactions", transactions);
            return "transaction-history";
        } else {
            model.addAttribute("error", "Không tìm thấy tài khoản!");
            return "dashboard";
        }
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String accountNumber, @RequestParam double amount, Model model) {
        Optional<Account> account = accountService.getAccountByNumber(accountNumber);
        if (account.isPresent()) {
            transactionService.deposit(accountNumber, amount);
            return "redirect:/transaction/" + accountNumber;
        } else {
            model.addAttribute("error", "Tài khoản không tồn tại!");
            return "dashboard";
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accountNumber, @RequestParam double amount, Model model) {
        Optional<Account> account = accountService.getAccountByNumber(accountNumber);
        if (account.isPresent()) {
            boolean success = transactionService.withdraw(accountNumber, amount);
            if (!success) {
                model.addAttribute("error", "Số dư không đủ!");
                return "dashboard";
            }
            return "redirect:/transaction/" + accountNumber;
        } else {
            model.addAttribute("error", "Tài khoản không tồn tại!");
            return "dashboard";
        }
    }
    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccountNumber,
                           @RequestParam String toAccountNumber,
                           @RequestParam double amount,
                           Model model) {
        boolean success = transactionService.transfer(fromAccountNumber, toAccountNumber, amount);
        if (!success) {
            model.addAttribute("error", "Số dư không đủ hoặc tài khoản không tồn tại!");
            return "dashboard";
        }
        return "redirect:/transaction/" + fromAccountNumber;
    }

}
