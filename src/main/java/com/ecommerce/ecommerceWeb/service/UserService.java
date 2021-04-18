package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void registerUser(UserDto userDto);

    void setPassword(String password, String rePassword);

    void authorization(String email, String password);

    String resetPassword(String pin);
}
