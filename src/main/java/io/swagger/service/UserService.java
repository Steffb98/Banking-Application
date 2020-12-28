package io.swagger.service;

import io.swagger.exception.AlreadyExistsException;
import io.swagger.exception.NotAuthorizedException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.model.TypeofuserEnum;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static io.swagger.model.Account.TypeofaccountEnum.DEPOSIT;
import static io.swagger.model.Account.TypeofaccountEnum.SAVING;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final AuthorizationService authorizationService;

    @Autowired
    public UserService(UserRepository userRepository, AccountService accountService, AuthorizationService authorizationService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.authorizationService = authorizationService;
    }

    public User getLoggedInUser() {
        Object security = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) security;
    }

    public User getUserById(Long userId) throws NotFoundException, NotAuthorizedException {
        authorizationService.checkUserAuthorization(userId);
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new NotFoundException(404, "User not found");
        }
        return user;
    }

    @Transactional(rollbackOn = Exception.class)
    public void createUser(User user) throws AlreadyExistsException, NotFoundException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new AlreadyExistsException(409, "Username already exists");
        }
        User createdUser = userRepository.save(new User(user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), TypeofuserEnum.CUSTOMER));

        //creating 2 accounts for each created user
        accountService.createAccount(new Account(accountService.generateIban(), SAVING, createdUser.getuserId()));
        accountService.createAccount(new Account(accountService.generateIban(), DEPOSIT, createdUser.getuserId()));
    }

    public void toggleUserStatus(Long userId) throws NotFoundException {
        User user = userRepository.findUserByUserId(userId);
        if (user != null) {
            //setting isActive to the opposite of the current value
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
        } else {
            throw new NotFoundException(404, "User not found");
        }
    }

    public void updateUser(Long userId, String password) throws NotFoundException, NotAuthorizedException {
        User user = userRepository.findUserByUserId(userId);
        authorizationService.checkUserAuthorization(userId);

        if (user == null) {
            throw new NotFoundException(404, "User not found");
        }
        if (!password.isEmpty()) {
            user.setPassword(password);
        }
        userRepository.save(user);
    }
}
