package com.bankmanagement.controller;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.AccountException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/saveAccount/{customerId}/{bankId}")
    public ResponseEntity<AccountDto> saveAccount(@Valid @RequestBody AccountDto accountdto, @PathVariable Long customerId, @PathVariable Long bankId) throws AccountException {
        return ResponseEntity.ok(accountService.saveAccount(accountdto, customerId, bankId));

    }

    @GetMapping("/getAllAccount")
    public ResponseEntity<List<AccountDto>> getAllAccountDetails() throws AccountException {
        return ResponseEntity.ok(accountService.getAllAccount());
    }
    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<String> updateAccountDto(@Valid @RequestBody AccountDto accountDto, @PathVariable("accountId") Long accountId) throws AccountException {

        return ResponseEntity.ok(accountService.updateAccountById(accountDto, accountId));
    }

    @DeleteMapping("/deleteAccountIdById/{accountId}")
    public String deleteAccountById(@PathVariable Long accountId) throws AccountException {
        return accountService.deleteAccountById(accountId);
    }

    @GetMapping("/checkAmountById/{accountId}")
    public ResponseEntity<List<Double>> balanceCheck(@PathVariable Long accountId) throws AccountException {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> depositAmount(@PathVariable Long accountId, @RequestBody Map<String, Double> request) throws AccountException {
        Double amount = request.get("amount");

        return ResponseEntity.ok(accountService.deposit(accountId, amount));
    }

    @PostMapping("/{accountId}/withdrawalAmount")
    public ResponseEntity<String> withdrawalAmount(@PathVariable Long accountId, @RequestBody Map<String, Double> request) throws AccountException {
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.withdrawalAmountById(accountId, amount));

    }
    @GetMapping("/blockAccountOrNot/{accountId}")
    public ResponseEntity<String> blockAccountCheck(@PathVariable Long accountId) throws AccountException {
        return ResponseEntity.ok(accountService.isBlocked(accountId));
    }


}
