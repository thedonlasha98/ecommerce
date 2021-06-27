package com.ecommerce.web.jwt.response;

public enum JwtMessage {

    EMAIL_IS_ALREADY_IN_USE("Error: Email is already in use!"),
    ROLE_IS_NOT_FOUND("Error: Role is not found!"),
    PASSWORD_NOT_EQUALS("password not equals"),
    INCORRECT_CREDENTIALS("Incorrect Credentials"),
    NOT_FOUND("Not Found"),
    GENERAL_ERROR("General Error!");

    String description;

    public String getDesc() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    JwtMessage(String description) {
        this.description = description;
    }
}
