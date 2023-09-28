package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.AccountDto;

import com.bankmanagement.entity.Account;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountRepository accountRepository;
    @Override
    public AccountDto saveAccount(AccountDto accountdto) {
        Account account = new Account();
       // AccountDto accountdto = new AccountDto();

        BeanUtils.copyProperties(accountdto,account);

        accountRepository.save(account);
        return accountdto;
    }

    @Override
    public List<AccountDto> getAllAccount() throws AccountException {
        if(accountRepository.findAll().isEmpty())
            throw new AccountException("customers Data not present in Database");
        return accountRepository.findAll().stream().filter(Objects::nonNull).map(account -> {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(account, accountDto);
            return accountDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> accountFindById(Long accountId) throws AccountException {
        if(accountRepository.findById(accountId).isEmpty())
            throw new AccountException("Account not present");
        return accountRepository.findById(accountId).stream().filter(Objects::nonNull)
                .map(account -> {
                    AccountDto accountDto = new AccountDto();
                    BeanUtils.copyProperties(account, accountDto);
                    return accountDto;
                }).collect(Collectors.toList());
    }

    @Override
    public AccountDto updateAccountById(AccountDto accountDto, Long accountId) {
        return null;
    }

    @Override
    public String deleteAccountById(Long accountId) {
        return null;
    }

    @Override
    public ResponseEntity<List<AccountDto>> getBalanceById(Double balance) {
        return null;
    }

    @Override
    public AccountDto depositAmount(AccountDto accountDto, Long accountId) throws AccountException
    {
        Optional <Account> optionalAccount= accountRepository.findById(accountId);
        if(optionalAccount.isEmpty())
            throw new AccountException("Account not Available");
         if(optionalAccount.isPresent())
        {
            Account account = optionalAccount.get();

            BeanUtils.copyProperties(accountDto,account);
            Double amt= account.getAmount();
            System.out.println(amt); //1000
            Double balance = amt+ accountDto.getAmount();
            System.out.println(balance);
            account.setAmount(balance);
             accountRepository.save(account);
        }
        return accountDto;
    }

    @Override
    public AccountDto withdrawalAmountById(Long accountId) {
        return null;
    }
}
