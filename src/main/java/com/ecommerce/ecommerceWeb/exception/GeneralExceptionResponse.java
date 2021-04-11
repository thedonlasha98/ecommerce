package com.ecommerce.ecommerceWeb.exception;

public class GeneralExceptionResponse {
    String message;
    public GeneralExceptionResponse(String message){this.message = message;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
