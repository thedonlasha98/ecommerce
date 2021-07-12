package com.ecommerce.web.exception;

public enum ErrorMessage {

    USER_BY_THIS_EMAIL_ALREADY_EXISTS("UserTest by this email, already exists!"),

    USER_BY_THIS_PIN_ALREADY_EXISTS("UserTest by this pin, already exists!"),

    PASSWORD_NOT_EQUALS("password not equals"),

    INCORRECT_CREDENTIALS("Incorrect Credentials"),

    NOT_FOUND("Not Found"),

    PRODUCTS_USER_AND_USER_NOT_EQUALS("Product's user and user not equals!"),

    PRODUCT_NOT_FOUND_WITH_PROVIDED_ID_AND_USER_ID("Product not found with provided id and userId"),

    NOT_ENOUGH_QUANTITY_IN_STOCK("Not enough quantity in stock!"),

    NOT_ENOUGH_BALANCE("Not enough balance!"),

    INCORRECT_CARD_CREDENTIALS("Incorrect card credentials!"),

    GENERAL_ERROR("General Error!");

    String description;



    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    ErrorMessage(String description) {
        this.description = description;
    }
}