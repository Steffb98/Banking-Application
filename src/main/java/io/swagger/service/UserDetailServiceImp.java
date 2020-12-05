//package io.swagger.service;
//
//import io.swagger.model.TypeofuserEnum;
//import io.swagger.model.User;
//import io.swagger.repository.UserRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.stereotype.Service;
//
//import java.util.Properties;
//
//@Service
//public class UserDetailServiceImp implements UserDetailsService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//    public UserDetailServiceImp(PasswordEncoder passwordEncoder, UserRepository userRepository) {
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = new User("customer", "henkie", "customer", "password");
//        User employee = new User(1L, "employee", "employee", "employee", "password", true, TypeofuserEnum.EMPLOYEE);
//        return null;
//    }
//}
