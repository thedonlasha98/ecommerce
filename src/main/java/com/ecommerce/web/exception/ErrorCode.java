package com.ecommerce.web.exception;

public enum ErrorCode {

    USER_BY_THIS_EMAIL_ALREADY_EXISTS("UserTest by this email, already exists!"),

    USER_BY_THIS_PIN_ALREADY_EXISTS("UserTest by this pin, already exists!"),

    PASSWORD_NOT_EQUALS("password not equals"),

    INCORRECT_CREDENTIALS("Incorrect Credentials"),

    NOT_FOUND("Not Found"),

    PRODUCTS_USER_AND_USER_NOT_EQUALS("Product's user and user not equals!"),

    NOT_ENOUGH_QUANTITY_IN_STOCK("Not enough quantity in stock!"),

    NOT_ENOUGH_BALANCE("Not enough balance!"),

    INCORRECT_CARD_CREDENTIALS("Incorrect card credentials!"),

    TRANSACTIONAL_EXCEPTION("Transactional Exception!");

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
            case PRODUCTS_USER_AND_USER_NOT_EQUALS:
                return ErrorCode.PRODUCTS_USER_AND_USER_NOT_EQUALS;
            case TRANSACTIONAL_EXCEPTION:
                return ErrorCode.TRANSACTIONAL_EXCEPTION;
            case NOT_ENOUGH_QUANTITY_IN_STOCK:
                return ErrorCode.NOT_ENOUGH_QUANTITY_IN_STOCK;
            case NOT_ENOUGH_BALANCE:
                return ErrorCode.NOT_ENOUGH_BALANCE;
            case INCORRECT_CARD_CREDENTIALS:
                return ErrorCode.INCORRECT_CARD_CREDENTIALS;
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