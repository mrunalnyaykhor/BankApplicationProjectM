package com.bankmanagement.controller;
import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(UrlConstant.SAVE_ACCOUNT)
    public ResponseEntity<AccountDto> saveAccount(@Valid @RequestBody AccountDto accountdto, @PathVariable Long customerId, @PathVariable Long bankId) throws AccountException {
        return ResponseEntity.ok(accountService.saveAccount(accountdto, customerId, bankId));

    }

    @GetMapping(UrlConstant.GATE_ALL_ACCOUNT)
    public ResponseEntity<List<AccountDto>> getAllAccountDetails() throws AccountException {
        log.info("get all Account");
        return ResponseEntity.ok(accountService.getAllAccount());

    }
    @GetMapping(UrlConstant.GATE_ACCOUNT_BY_ID)
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId) throws AccountException {
        log.info("get  Account By Id");
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @PutMapping(UrlConstant.UPDATE_ACCOUNT_BY_ID)
    public ResponseEntity<String> updateAccountDto(@Valid @RequestBody AccountDto accountDto, @PathVariable("accountId") Long accountId) throws AccountException {
        log.info("update Account");
        return ResponseEntity.ok(accountService.updateAccountById(accountDto, accountId));
    }

    @DeleteMapping(UrlConstant.DELETE_ACCOUNT)
    public String deleteAccountById(@PathVariable Long accountId) throws AccountException {
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


}
