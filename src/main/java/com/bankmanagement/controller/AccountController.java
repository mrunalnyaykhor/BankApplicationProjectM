package com.bankmanagement.controller;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.exception.AccountException;
import com.bankmanagement.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@CrossOrigin(origins = { "http://localhost:4200/" })
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(UrlConstant.SAVE_ACCOUNT)
    public ResponseEntity<ResponseEntity<String>> saveAccount(@Valid @RequestBody AccountDto accountdto) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(accountService.saveAccount(accountdto));

    }

    @GetMapping(UrlConstant.GATE_ALL_ACCOUNT)
    public ResponseEntity<List<Account>> getAllAccountDetails() throws AccountException {
        return ResponseEntity.ok(accountService.getAllAccount());

    }

    @GetMapping(UrlConstant.GATE_ACCOUNT_BY_ID)
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId) throws AccountException {


        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @PutMapping(UrlConstant.UPDATE_ACCOUNT_BY_ID)
    public ResponseEntity<ResponseEntity<String>> updateAccountDto(@Valid @RequestBody Account account) throws AccountException {
        log.info(ApplicationConstant.ACCOUNT_ID_UPDATE_SUCCESSFULLY);
        return ResponseEntity.ok(accountService.updateAccountById(account));
    }

    @DeleteMapping(UrlConstant.DELETE_ACCOUNT)
    public String deleteAccountById(@PathVariable Long accountId)  {
        return accountService.deleteAccountById(accountId);
    }

    @GetMapping(UrlConstant.BALANCE_CHECK)
    public ResponseEntity<Double> balanceCheck(@PathVariable Long accountId) throws AccountException {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping(UrlConstant.DEPOSIT_AMOUNT)
    public ResponseEntity<String> depositAmount(@PathVariable Long accountId, @RequestBody Map<String, Double> request) throws AccountException {
        Double amount = request.get("amount");

        return ResponseEntity.ok(accountService.deposit(accountId, amount));
    }

    @PostMapping(UrlConstant.WITHDRAWAL_AMOUNT)
    public ResponseEntity<String> withdrawalAmount(@PathVariable Long accountId, @RequestBody Map<String, Double> request) throws AccountException {
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.withdrawalAmountById(accountId, amount));

    }


    @GetMapping(UrlConstant.ACCOUNT_BLOCK_UNBLOCK_CHECK)
    public ResponseEntity<String> blockAccountCheck(@PathVariable Long accountId) throws AccountException {
        return ResponseEntity.ok(accountService.isBlocked(accountId));
    }

    @GetMapping("/getAllSavingAccount")
    public ResponseEntity<List<Account>> getAccountType(){
        return ResponseEntity.ok(accountService.getAllSavingAccount());
    }
    @GetMapping("/myaccount/{accountNumber}")
    public ResponseEntity<AccountDto> getMyAccountDetails(@PathVariable Long accountNumber){
        return  ResponseEntity.ok(accountService.getMyAccountDetails( accountNumber));
    }


}
