package io.swagger.service;

import io.swagger.exception.AlreadyExistsException;
import io.swagger.exception.BadInputException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
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

    public User getUserById(Long userId) throws NotFoundException {
        User user = userRepository.findUserByUserId(userId);
        if(user == null){
            throw new NotFoundException(404, "User not found");
        }
        return user;
    }

    public void createUser(User user) throws AlreadyExistsException, BadInputException {
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new AlreadyExistsException(409, "Email already exists");
        }
        else if(!EmailValidator.getInstance().isValid(user.getEmail())){
            throw new BadInputException(400, "Email format is incorrect");
        }
        userRepository.save(new User(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword()));
    }

    public void toggleUserStatus(Long userId) throws NotFoundException {
        User user = userRepository.findUserByUserId(userId);
        if(user == null) {
            throw new NotFoundException(404, "User not found");
        }
        //setting isActive to the opposite of the current value
        user.setIsactive(!user.isIsactive());
        userRepository.save(user);
    }

    public void updateUser(Long userId, User body) throws NotFoundException, BadInputException {
        User user = userRepository.findUserByUserId(userId);
        if(user == null) {
            throw new NotFoundException(404, "User not found");
        }
        else if(!EmailValidator.getInstance().isValid(body.getEmail())){
            throw new BadInputException(400, "Email format is incorrect");
        }
        if (!body.getFirstname().isEmpty()) {
            user.setFirstname(body.getFirstname());
        }
        if (!body.getLastname().isEmpty()) {
            user.setLastname(body.getLastname());
        }
        if (!body.getEmail().isEmpty()) {
            user.setEmail(body.getEmail());
        }
        if (!body.getPassword().isEmpty()) {
            user.setPassword(body.getPassword());
        }
        userRepository.save(user);

    }
}
