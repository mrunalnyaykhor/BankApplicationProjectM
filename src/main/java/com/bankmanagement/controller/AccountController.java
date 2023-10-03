package com.bankmanagement.controller;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.UserTransaction;
import com.bankmanagement.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/saveAccount")
    public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto accountdto)
    {
        return ResponseEntity.ok( accountService.saveAccount(accountdto));

    }
    @GetMapping("/getAllAccount")
    public ResponseEntity<List<AccountDto>> getAllAccountDetails() throws AccountException {
        return ResponseEntity.ok(accountService.getAllAccount());
    }
    @GetMapping("/getAccountById/{accountId}")
    public ResponseEntity<List <AccountDto>> getAccountById(@PathVariable Long accountId) throws AccountException {
        return ResponseEntity.ok(accountService.accountFindById(accountId));
    }
    @PutMapping("/account/{accountId}")
    public ResponseEntity<AccountDto> updateAccountDto(@RequestBody AccountDto accountDto, @PathVariable("accountId") Long accountId) throws AccountException {

        return ResponseEntity.ok(accountService.updateAccountById(accountDto, accountId));
    }
    @DeleteMapping("/deleteAccountIdById/{accountId}")
    public String deleteAccountById(@PathVariable Long accountId)
    {
        return accountService.deleteAccountById(accountId);
    }
    @GetMapping("/checkAmountById/{accountId}")
    public ResponseEntity<List<AccountDto>> balanceCheck(@PathVariable Double amount) throws AccountException {
        return ResponseEntity.ok(accountService.getAmountById(amount));
    }

    @PostMapping("/{accountId}/deposit")
    public Account deposit(@PathVariable Long accountId, @RequestBody Map<String, Double> request) throws AccountException {
        Double amount = request.get("amount");

        return accountService.deposit(accountId, amount);
    }
    @PostMapping("/{accountId}/withdrawalAmount")
    public ResponseEntity<Account> withdrawalAmount(@PathVariable Long accountId,@RequestBody Map<String, Double> request) throws AccountException {
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.withdrawalAmountById(accountId,amount));

    }







}
