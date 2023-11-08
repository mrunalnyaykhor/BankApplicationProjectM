package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AccountIntegrationTest {
    private static AccountDto accountDto;
    private static Account account;
    private static Map deposit ;
    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    ObjectMapper objectMapper;
    @LocalServerPort
    private int port;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() throws IOException {
        account = objectMapper.readValue(new ClassPathResource("account.json").getInputStream(), Account.class);
        accountDto = objectMapper.readValue(new ClassPathResource("accountDto.json").getInputStream(), AccountDto.class);
       deposit = objectMapper.readValue(new ClassPathResource("deposit.json").getInputStream(),Map.class);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


    @Test
    public void accountSaveIntegrationTest() {

        String URIToSaveAccount = "/saveAccount/41/12";
        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveAccount), HttpMethod.POST, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getAllAccountTest() {
        String getAllAccount = "/getAllAccount";

        String getAccountResponse = restTemplate.getForObject(formFullURLWithPort(getAllAccount), String.class);

        assertNotNull(getAccountResponse, "Response body should not be null");
    }

    @Test
    public void getAccountById() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(formFullURLWithPort("/getAccountById/45/"), String.class);
        assertNotNull(responseEntity, "Response body should not be null");
    }
    @Test
    public void updateAccountById() {
        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/accounts/45"), HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    public void deleteAccountIntegrationTest() {

        Optional<Account> customer1 = accountRepository.findById(account.getAccountId());
        restTemplate.delete(formFullURLWithPort("/deleteAccountIdById/45"));
    }
    @Test
    public void balanceCheckAccountId(){
        Optional<Account> byId = accountRepository.findById(account.getAccountId());
        ResponseEntity<String> response = restTemplate.getForEntity(formFullURLWithPort("/checkAmountById/45/"), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


@Test
    public void depositAmount(){

    HttpEntity<Map> account1 = new HttpEntity<>(deposit,headers);
    ResponseEntity<Map> response = restTemplate.exchange(formFullURLWithPort("/deposit/45"),HttpMethod.POST,account1, Map.class);
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
}

@Test
public void blockAccountOrNotCheck(){
    Optional<Account> byId = accountRepository.findById(account.getAccountId());
    ResponseEntity<String> response = restTemplate.getForEntity(formFullURLWithPort("/blockAccountOrNot/45/"), String.class);
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

}


}

