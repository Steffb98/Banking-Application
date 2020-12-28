package io.swagger.service;

import io.swagger.exception.NotAuthorizedException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    protected void checkUserAuthorization(Long userId) throws NotAuthorizedException {
        Object security = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (((User) security).getuserId().equals(userId) || ((User) security).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            return;
        }

        throw new NotAuthorizedException(401, "not authorized");
    }

    protected void checkAccountAuthorization(Account account) throws NotAuthorizedException {
        Object security = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (((User) security).getuserId().equals(account.getUserid()) || ((User) security).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            return;
        }

        throw new NotAuthorizedException(401, "not authorized");
    }

    protected void checkTransactionAuthorization(Transaction transaction) throws NotAuthorizedException {
        Object security = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (((User) security).getuserId().equals(transaction.getPerforminguser()) ||
                ((User) security).getuserId().equals(transaction.getReceivinguser()) ||
                ((User) security).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            return;
        }

        throw new NotAuthorizedException(401, "not authorized");
    }
}
