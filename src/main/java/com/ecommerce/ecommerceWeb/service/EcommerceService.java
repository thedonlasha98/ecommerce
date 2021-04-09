package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.UserDto;

public interface EcommerceService {

    void registerUser(UserDto userDto);

    void setPassword(String password, String rePassword);

    void authorization(String email, String password);

    String resetPassword(String pin);
}
