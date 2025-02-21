package org.example.atm.Service;

import org.example.atm.Model.Account;
import org.example.atm.Model.Transaction;
import org.example.atm.Respository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }

    public void deposit(String accountNumber, double amount) {
        accountService.updateBalance(accountNumber, amount);

        Transaction transaction = new Transaction();
        transaction.setAccount(accountService.getAccountByNumber(accountNumber).get());
        transaction.setAmount(amount);
        transaction.setType("Deposit");
        transaction.setTimeTrans(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = accountService.getAccountByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        if (account.getBalance() < amount) {
            return false;
        }

        accountService.updateBalance(accountNumber, -amount);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType("Withdraw");
        transaction.setTimeTrans(LocalDateTime.now());
        transactionRepository.save(transaction);
        return true;
    }
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accountService.getAccountByNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Tài khoản gửi không tồn tại"));
        Account toAccount = accountService.getAccountByNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Tài khoản nhận không tồn tại"));

        if (fromAccount.getBalance() < amount) {
            return false;
        }

        accountService.updateBalance(fromAccountNumber, -amount);
        accountService.updateBalance(toAccountNumber, amount);

        Transaction fromTransaction = new Transaction();
        fromTransaction.setAccount(fromAccount);
        fromTransaction.setAmount(-amount);
        fromTransaction.setType("Transfer Out");
        fromTransaction.setTimeTrans(LocalDateTime.now());
        transactionRepository.save(fromTransaction);

        Transaction toTransaction = new Transaction();
        toTransaction.setAccount(toAccount);
        toTransaction.setAmount(amount);
        toTransaction.setType("Transfer In");
        toTransaction.setTimeTrans(LocalDateTime.now());
        transactionRepository.save(toTransaction);

        return true;
    }

}

