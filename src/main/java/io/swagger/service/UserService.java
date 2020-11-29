package io.swagger.service;

import io.swagger.exception.AlreadyExistsException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
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

    public void createUser(User user) throws AlreadyExistsException {
        if(userRepository.findByEmail(user.getEmail()) == null){
            userRepository.save(new User(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword()));
        }else{
            throw new AlreadyExistsException(409, "Email already exists");
        }
    }

    public void toggleUserStatus(Long userId) throws NotFoundException {
        User user = userRepository.findUserByUserId(userId);
        if(user != null){
            //setting isActive to the opposite of the current value
            user.setIsactive(!user.isIsactive());
            userRepository.save(user);
        }else{
            throw new NotFoundException(404, "User not found");
        }
    }

    public void updateUser(Long userId, User body) throws NotFoundException {
        User user = userRepository.findUserByUserId(userId);
        if(user == null) {
            throw new NotFoundException(404, "User not found");
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
