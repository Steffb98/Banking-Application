package io.swagger.api;

import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")
@RestController
public class AccountApiController implements AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createAcc(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Account body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Account>> getAccountByIban(@Parameter(in = ParameterIn.PATH, description = "Id of account", required=true, schema=@Schema()) @PathVariable("accountId") String accountId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Account>>(objectMapper.readValue("[ {\n  \"daylimit\" : 5,\n  \"numberoftransactions\" : 0,\n  \"balance\" : 10.00,\n  \"transactionlimit\" : 20000.00,\n  \"iban\" : \"NL00RABO0123456789\",\n  \"isactive\" : true,\n  \"typeofaccount\" : \"saving\",\n  \"userid\" : 0,\n  \"absolutlimit\" : -10.00\n}, {\n  \"daylimit\" : 5,\n  \"numberoftransactions\" : 0,\n  \"balance\" : 10.00,\n  \"transactionlimit\" : 20000.00,\n  \"iban\" : \"NL00RABO0123456789\",\n  \"isactive\" : true,\n  \"typeofaccount\" : \"saving\",\n  \"userid\" : 0,\n  \"absolutlimit\" : -10.00\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Account>> getAccountByUserID(@Parameter(in = ParameterIn.PATH, description = "Id of user", required=true, schema=@Schema()) @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Account>>(objectMapper.readValue("[ {\n  \"daylimit\" : 5,\n  \"numberoftransactions\" : 0,\n  \"balance\" : 10.00,\n  \"transactionlimit\" : 20000.00,\n  \"iban\" : \"NL00RABO0123456789\",\n  \"isactive\" : true,\n  \"typeofaccount\" : \"saving\",\n  \"userid\" : 0,\n  \"absolutlimit\" : -10.00\n}, {\n  \"daylimit\" : 5,\n  \"numberoftransactions\" : 0,\n  \"balance\" : 10.00,\n  \"transactionlimit\" : 20000.00,\n  \"iban\" : \"NL00RABO0123456789\",\n  \"isactive\" : true,\n  \"typeofaccount\" : \"saving\",\n  \"userid\" : 0,\n  \"absolutlimit\" : -10.00\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> toggleStatusAcc(@Parameter(in = ParameterIn.PATH, description = "AccountID to set to active or inactive", required=true, schema=@Schema()) @PathVariable("accountId") String accountId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
