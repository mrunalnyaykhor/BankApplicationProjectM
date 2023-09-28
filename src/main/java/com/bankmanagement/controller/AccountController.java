package com.bankmanagement.controller;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.UserTransaction;
import com.bankmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.List;

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
    public ResponseEntity<AccountDto> updateAccountDto(@RequestBody AccountDto accountDto, @PathVariable("accountId") Long accountId) {

        return ResponseEntity.ok(accountService.updateAccountById(accountDto, accountId));
    }
    @DeleteMapping("/deleteAccountIdById/{accountId}")
    public String deleteAccountById(@PathVariable Long accountId)
    {
        return accountService.deleteAccountById(accountId);
    }
    @GetMapping("/checkBalanceById/{accountId}")
    public ResponseEntity<List<AccountDto>> balanceCheck(@PathVariable Double balance){
        return accountService.getBalanceById(balance);
    }
    @PutMapping("/depositAmount/{accountId}")
    public ResponseEntity <AccountDto>DepositMoney(@RequestBody AccountDto accountDto,@PathVariable Long accountId) throws AccountException {
        return ResponseEntity.ok(accountService.depositAmount(accountDto,accountId));
    }
    @GetMapping("/withdrawalAmount/{accountId}")
    public ResponseEntity<AccountDto> withdrawalAmount(@PathVariable Long accountId){
        return ResponseEntity.ok(accountService.withdrawalAmountById(accountId));

    }

}
