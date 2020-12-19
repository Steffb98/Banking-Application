package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.exception.BadInputException;
import io.swagger.exception.ForbiddenException;
import io.swagger.exception.NotAuthorizedException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")
@RestController
public class AccountApiController implements AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final AccountService accountService;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request, AccountService accountService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.accountService = accountService;
    }

    public ResponseEntity createAcc(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Account body, Errors errors) throws BadInputException, NotFoundException {
        /*
         * If the enum in the body is not a valid type of account, an error is thrown before we can catch it, so
         * we're using the Errors class to check if any errors occur while parsing the json body
        */
        if (errors.hasErrors()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Type of account is not valid");
        }
        try{
            accountService.createAccount(body);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Account succesfully created");
        } catch (NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAccountByIban(@Parameter(in = ParameterIn.PATH, description = "Iban of account", required=true, schema=@Schema()) @PathVariable("accountId") String accountId) throws NotFoundException {
        try {
            return new ResponseEntity<Account>(accountService.getAccountByIban(accountId), HttpStatus.OK);
        } catch (ForbiddenException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (NotAuthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }catch(NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch(BadInputException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAccountByUserID(@Parameter(in = ParameterIn.PATH, description = "Id of user", required=true, schema=@Schema()) @PathVariable("userId") Long userId) {
        try {
            return new ResponseEntity<List<Account>>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
        } catch(NotAuthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }catch(NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch(BadInputException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity toggleStatusAcc(@Parameter(in = ParameterIn.PATH, description = "AccountID to set to active or inactive", required=true, schema=@Schema()) @PathVariable("accountId") String accountId) {
        try {
            accountService.toggleActivityStatus(accountId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Account status is succesfully toggled");
        } catch (ForbiddenException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (BadInputException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
