package io.swagger.service;

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

    public User getUserById(Long userId)
    {
        return userRepository.findUserById(userId);
    }

    public HttpStatus createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()) == null){
            userRepository.save(user);
            return HttpStatus.OK;
        }else{
            return HttpStatus.FOUND;
        }
    }

    public HttpStatus toggleUserStatus(Long userId) {
        User user = userRepository.findUserById(userId);
        if(user != null){
            user.setIsactive(!user.isIsactive());
            userRepository.save(user);
            return HttpStatus.OK;
        }else{
            return HttpStatus.NOT_FOUND;
        }
    }

    public HttpStatus updateUser(Long userId, User body) {
        User user = userRepository.findUserById(userId);
        if(user != null){
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
            return HttpStatus.OK;
        }else{
            return HttpStatus.NOT_FOUND;
        }
    }
}
