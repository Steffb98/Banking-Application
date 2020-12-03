package io.swagger.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static io.swagger.model.TypeofuserEnum.*;
import static io.swagger.configuration.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //TODO: dit wordt later verandert
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/search/**").hasAuthority(USER_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/user").hasAuthority(USER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/user/**").hasAuthority(USER_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/account/**").hasAuthority(ACCOUNT_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/account").hasAuthority(ACCOUNT_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/account/activity/**").hasAuthority(ACCOUNT_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/transaction/**").hasAuthority(TRANSACTION_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/transaction").hasAuthority(TRANSACTION_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails steffUser = User.builder()
                .username("customer")
                .password(passwordEncoder.encode("password"))
                .authorities(CUSTOMER.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder()
                .username("employee")
                .password(passwordEncoder.encode("password"))
                .authorities(EMPLOYEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                steffUser,
                adminUser
        );
    }
}
