package io.swagger.service;

import io.swagger.exception.AlreadyExistsException;
import io.swagger.exception.NotAuthorizedException;
import io.swagger.exception.BadInputException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.TypeofuserEnum;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkUserAuthorization(Long userId) throws NotAuthorizedException {
        Object security = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (((User) security).getuserId().equals(userId) || ((User) security).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))){
            return;
        }

        throw new NotAuthorizedException(401, "not authorized");
    }

    public User getLoggedInUser(){
        Object security = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) security;
    }

    public User getUserById(Long userId) throws NotFoundException, NotAuthorizedException {
        checkUserAuthorization(userId);
        User user = userRepository.findUserByUserId(userId);
        if(user == null){
            throw new NotFoundException(404, "User not found");
        }
        return user;
    }

    public void createUser(User user) throws AlreadyExistsException, BadInputException {
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new AlreadyExistsException(409, "Email already exists");
        }
        else if(!EmailValidator.getInstance().isValid(user.getUsername())){
            throw new BadInputException(400, "Email format is incorrect");
        }
        userRepository.save(new User(user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), TypeofuserEnum.CUSTOMER));
    }

    public void toggleUserStatus(Long userId) throws NotFoundException {
        User user = userRepository.findUserByUserId(userId);
        if(user != null){
            //setting isActive to the opposite of the current value
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
        }else{
            throw new NotFoundException(404, "User not found");
        }
        //setting isActive to the opposite of the current value
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    public void updateUser(Long userId, User body) throws NotFoundException, BadInputException {
        User user = userRepository.findUserByUserId(userId);
        if(user == null) {
            throw new NotFoundException(404, "User not found");
        }
        else if(!EmailValidator.getInstance().isValid(body.getUsername())){
            throw new BadInputException(400, "Email format is incorrect");
        }
        if (!body.getFirstname().isEmpty()) {
            user.setFirstname(body.getFirstname());
        }
        if (!body.getLastname().isEmpty()) {
            user.setLastname(body.getLastname());
        }
        if (!body.getUsername().isEmpty()) {
            user.setUsername(body.getUsername());
        }
        if (!body.getPassword().isEmpty()) {
            user.setPassword(body.getPassword());
        }
        userRepository.save(user);
    }
}
