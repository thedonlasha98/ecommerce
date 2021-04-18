package com.ecommerce.ecommerceWeb.exception;

public enum ErrorCode {

    USER_BY_THIS_EMAIL_ALREADY_EXISTS("UserTest by this email, already exists!"),

    USER_BY_THIS_PIN_ALREADY_EXISTS("UserTest by this pin, already exists!"),

    PASSWORD_NOT_EQUALS("password not equals"),

    INCORRECT_CREDENTIALS("Incorrect Credentials"),

    NOT_FOUND("Not Found");

    String description;

    public static ErrorCode getException(ErrorCode ex){
        switch (ex){
            case INCORRECT_CREDENTIALS:
                return ErrorCode.INCORRECT_CREDENTIALS;
            case PASSWORD_NOT_EQUALS:
                return ErrorCode.PASSWORD_NOT_EQUALS;
            case USER_BY_THIS_PIN_ALREADY_EXISTS:
                return ErrorCode.USER_BY_THIS_PIN_ALREADY_EXISTS;
            case USER_BY_THIS_EMAIL_ALREADY_EXISTS:
                return ErrorCode.USER_BY_THIS_EMAIL_ALREADY_EXISTS;
            default:
                return ErrorCode.NOT_FOUND;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    ErrorCode(String description) {
        this.description = description;
    }
}