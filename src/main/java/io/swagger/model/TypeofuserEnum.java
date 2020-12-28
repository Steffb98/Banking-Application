package io.swagger.model;

import com.google.common.collect.Sets;
import io.swagger.configuration.ApplicationUserPermission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static io.swagger.configuration.ApplicationUserPermission.*;

public enum TypeofuserEnum {
    CUSTOMER(Sets.newHashSet(USER_READ, USER_UPDATE, ACCOUNT_READ, TRANSACTION_READ, TRANSACTION_WRITE)),

    EMPLOYEE(Sets.newHashSet(USER_READ, USER_WRITE, USER_UPDATE, ACCOUNT_READ, ACCOUNT_WRITE, TRANSACTION_READ, TRANSACTION_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    TypeofuserEnum(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
