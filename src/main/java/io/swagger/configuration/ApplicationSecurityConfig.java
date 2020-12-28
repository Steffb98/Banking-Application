package io.swagger.configuration;

import io.swagger.service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static io.swagger.configuration.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImp userDetailsServiceImp;

    @Autowired
    public ApplicationSecurityConfig(UserDetailServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/style/**").permitAll()
                .antMatchers(HttpMethod.GET, "/user/search/**").hasAuthority(USER_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/user").hasAuthority(USER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/user").hasAuthority(USER_UPDATE.getPermission())
                .antMatchers(HttpMethod.PUT, "/user/activity/**").hasAuthority(USER_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/account/**").hasAuthority(ACCOUNT_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/account").hasAuthority(ACCOUNT_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/account/activity/**").hasAuthority(ACCOUNT_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/transaction/**").hasAuthority(TRANSACTION_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/transaction").hasAuthority(TRANSACTION_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/index.html")
                .loginProcessingUrl("/perform_login")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .permitAll();
    }
}
