package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.request.LoginRequest;
import com.ecommerce.ecommerceWeb.model.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

//    void registerUser(UserDto userDto);
//
String setPassword(Long userId, String password, String rePassword);
//
//    void authorization(String email, String password);
//
    String resetPassword(String pin);

    ResponseEntity signIn(LoginRequest loginRequest);

    ResponseEntity signUp(SignupRequest signUpRequest);
}
