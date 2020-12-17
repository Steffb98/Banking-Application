package io.swagger.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import org.threeten.bp.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class StepDefinitions {

    private Transaction transaction;
    private User user;
    private Account account;

    RestTemplate template = new RestTemplate();
    ResponseEntity<String> responseEntity;
    String response;

    HttpHeaders headers = new HttpHeaders();
    String baseUrl = "http://localhost:8080/";

    // USER TESTS!!!

//    @When("I update user with userId {int}")
//    public void iUpdateUserWithUserId(int userId) throws URISyntaxException {
//        URI uri = new URI(baseUrl + "user/" + userId);
//        try{
//
//            responseEntity = new ResponseEntity<String>(HttpStatus.OK);
//            user = new User("Bert", "Jan", "123456@student.inholland.nl", "ola123!");
//            ObjectMapper mapper = new ObjectMapper();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
//            responseEntity = template.postForEntity(uri, entity, String.class);
//        } catch (RestClientException | JsonProcessingException RCE){
//            responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @When("I retrieve user with userId {int}")
    public void iRetrieveUserWithId(int userId) throws URISyntaxException {
        URI uri = new URI(baseUrl + "user/search/" + userId);
        try {
            responseEntity = template.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e) {
            byte[] bytes = e.getResponseBodyAsByteArray();

            //Convert byte[] to String
            String s = new String(bytes);
            responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            System.out.println(s);
        }
    }

    @When("I post an user")
    public void iPostAnUser() throws JsonProcessingException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        user = new User("Bert", "Jan", "123456@student.inholland.nl", "ola123!");
        URI uri = new URI(baseUrl + "user");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
    }

    @When("I Change the status of an user with userId {int}")
    public void iChangeUserStatusWithId(int userId) throws URISyntaxException, JsonProcessingException {
        URI uri = new URI(baseUrl + "user/activity/" + userId);
        try{
            template.put(uri, String.class);
            responseEntity = new ResponseEntity<String>(HttpStatus.OK);
        } catch (RestClientException RCE){
            responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

    }

    // ACCOUNT TESTS!!

    @When("I change the activity from an account with id {string}")
    public void iChangeTheActivityFromAnAccountWithId(String iban) throws URISyntaxException {
        URI uri = new URI(baseUrl + "account/activity/" + iban);

        try{
            template.put(uri, String.class);
            responseEntity = new ResponseEntity<String>(HttpStatus.OK);
        } catch (RestClientException RCE){
            responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Then("I get a list of {int} accounts")
    public void iGetAListOfAccounts(int size) throws JSONException {
        response = responseEntity.getBody();
        JSONArray array = new JSONArray(response);
        Assert.assertEquals(size, array.length());
    }

    @When("I retrieve account with id {string}")
    public void iRetrieveAccountWithId(String id) throws URISyntaxException {
        URI uri = new URI(baseUrl + "account/" + id);
        try {
            responseEntity = template.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e) {
            byte[] bytes = e.getResponseBodyAsByteArray();

            //Convert byte[] to String
            String s = new String(bytes);
            responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            System.out.println(s);
        }
    }

    @When("I post an account")
    public void iPostAnAccount() throws JsonProcessingException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        account = new Account("NL44INHO0123456789", BigDecimal.valueOf(0.00), Account.TypeofaccountEnum.SAVING, BigDecimal.valueOf(-10.00), true, 100002L, 5L, new BigDecimal(20000), 5L);
        URI uri = new URI(baseUrl + "account");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(account), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
    }

    @When("I retrieve account with userId {int}")
    public void iRetrieveAccountWithUserId(int userId) throws URISyntaxException {
        URI uri = new URI(baseUrl + "account/listaccount/" + userId);
        try {
            responseEntity = template.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e) {
            byte[] bytes = e.getResponseBodyAsByteArray();

            //Convert byte[] to String
            String s = new String(bytes);
            responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            System.out.println(s);
        }

    }
    // TRANSACTION TESTS!!


    @Then("I get http status {int}")
    public void iGetHttpStatus(int status){
        Assert.assertEquals(responseEntity.getStatusCodeValue(), status);
    }

    @Then("I get a list of {int} transactions")
    public void iGetAListOfTransactions(int size) throws JSONException {
        response = responseEntity.getBody();
        JSONArray array = new JSONArray(response);
        Assert.assertEquals(size, array.length());
    }

    @When("I retrieve transaction with id {int}")
    public void iRetrieveTransactionWithId(int id) throws URISyntaxException {
        URI uri = new URI(baseUrl + "transaction/" + id);
        try {
            responseEntity = template.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e){
            byte[] bytes = e.getResponseBodyAsByteArray();

            //Convert byte[] to String
            String s = new String(bytes);
            responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            System.out.println(s);
        }
    }

    @Then("The transaction amount is {string}")
    public void theTransactionAmountIs(String amount) throws JSONException {
        response = responseEntity.getBody();
        Assert.assertEquals(amount,
                new JSONObject(response)
                        .getString("amount"));
    }

//    @When("I post a transaction")
//    public void iPostATransaction() throws JsonProcessingException, URISyntaxException {
//        ObjectMapper mapper = new ObjectMapper();
//        transaction = new Transaction(10l,"NL35INHO0919663403", "NL05INHO0800426579", BigDecimal.valueOf(10), 100001L, OffsetDateTime.now());
//        URI uri = new URI(baseUrl + "transaction");
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(transaction), headers);
//        responseEntity = template.postForEntity(uri, entity, String.class);
//    }

    @When("I retrieve transaction from userId {int}")
    public void iRetrieveTransactionFromUserWithId(int userId) throws URISyntaxException {
        URI uri = new URI(baseUrl + "transaction/user/" + userId);
        responseEntity = template.getForEntity(uri, String.class);
    }

    @When("I retrieve transaction from account {string}")
    public void iRetrieveTransactionFromAccountWithId(String id) throws URISyntaxException {
        URI uri = new URI(baseUrl + "transaction/account/" + id);
        responseEntity = template.getForEntity(uri, String.class);
    }


}
