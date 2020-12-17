package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.exception.AlreadyExistsException;
import io.swagger.exception.NotAuthorizedException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.User;
import io.swagger.service.UserService;
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
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request, UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.userService = userService;
    }

    public ResponseEntity createUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody User body) {
        try {
            userService.createUser(body);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User has been created");
        }catch(AlreadyExistsException e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getUserByUserId(@Parameter(in = ParameterIn.PATH, description = "userId of an user", required=true, schema=@Schema()) @PathVariable("userId") Long userId) {
        try {
            return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
        } catch (NotAuthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }catch(NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity toggleUserStatus(@Parameter(in = ParameterIn.PATH, description = "The userID that needs to be set active or inactive", required=true, schema=@Schema()) @PathVariable("userId") Long userId) {
        try {
            userService.toggleUserStatus(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User activity has been changed");
        }catch(NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity updateUser(@Parameter(in = ParameterIn.PATH, description = "id of user that needs to be updated", required=true, schema=@Schema()) @PathVariable("userId") Long userId,@Parameter(in = ParameterIn.DEFAULT, description = "Updated user object", required=true, schema=@Schema()) @Valid @RequestBody User body) {
        try {
            userService.updateUser(userId, body);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User has been updated");
        }catch(NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
