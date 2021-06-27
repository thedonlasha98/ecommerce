package com.ecommerce.web.jwt.service;

import com.ecommerce.web.domain.User;
import com.ecommerce.web.jwt.request.LoginRequest;
import com.ecommerce.web.jwt.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

//    void registerUser(UserDto userDto);
//
String setPassword(String hash, String password, String rePassword);
//
//    void authorization(String email, String password);
//
    String resetPassword(String pin);

    ResponseEntity signIn(LoginRequest loginRequest);

    ResponseEntity signUp(SignupRequest signUpRequest);

    User getUserByEmail(String email);
}
