package io.swagger.exception;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")
public class NotAuthorizedException extends ApiException {
    private int code;
    public NotAuthorizedException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
