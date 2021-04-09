package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.UserDto;

public interface EcommerceService {
    void registerUser(UserDto userDto);
    public void setPassword(String password, String rePassword);
}
