package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.exception.*;
import io.swagger.model.Transaction;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")
@RestController
public class TransactionApiController implements TransactionApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private TransactionService transactionService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService transactionService, AccountService accountService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.transactionService = transactionService;
    }

    public ResponseEntity addTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Transaction body) throws NotFoundException ,BadInputException, LimitReachedException {
        try {
            System.out.println("test");
            transactionService.addTransaction(body);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Transaction Created");

        } catch (ForbiddenException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (NotAuthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (NotFoundException nfe){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(nfe.getMessage());
        } catch (BadInputException bie){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(bie.getMessage());
        } catch (LimitReachedException lre){
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(lre.getMessage());
        }
    }

    public ResponseEntity getTransactionById(@Parameter(in = ParameterIn.PATH, description = "ID of transaction to return", required=true, schema=@Schema()) @PathVariable("transactionId") Long transactionId) throws NotFoundException {
        try {
            return new ResponseEntity<Transaction>(transactionService.getTransactionById(transactionId), HttpStatus.OK);
        } catch (NotAuthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (NotFoundException nfe){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(nfe.getMessage());
        }
    }

    public ResponseEntity getTransactionFromAccount(@Parameter(in = ParameterIn.PATH, description = "ID of an account", required=true, schema=@Schema()) @PathVariable("accountId") String accountId) throws NotFoundException, BadInputException{
        try {
            return new ResponseEntity(transactionService.getAllTransactionsFromAccount(accountId), HttpStatus.OK);
        } catch (ForbiddenException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (NotAuthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }catch (NotFoundException nfe){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(nfe.getMessage());
        } catch (BadInputException bie){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(bie.getMessage());
        }
    }

    public ResponseEntity getTransactionFromUser(@Parameter(in = ParameterIn.PATH, description = "ID of a user", required=true, schema=@Schema()) @PathVariable("userId") Long userId) throws NotFoundException{
        try {
            return new ResponseEntity(transactionService.getAllTransactionsFromUser(userId), HttpStatus.OK);
        } catch (NotAuthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (NotFoundException nfe){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(nfe.getMessage());
        }
    }
}