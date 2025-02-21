package org.example.atm.Service;

import lombok.RequiredArgsConstructor;
import org.example.atm.Model.Account;
import org.example.atm.Model.Transaction;
import org.example.atm.Respository.AccountRepository;
import org.example.atm.Respository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public void updateBalance(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }
}

