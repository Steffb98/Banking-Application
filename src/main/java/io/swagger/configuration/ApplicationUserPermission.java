package io.swagger.configuration;

public enum ApplicationUserPermission {
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_WRITE("user:write"),
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write"),
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_WRITE("transaction:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
