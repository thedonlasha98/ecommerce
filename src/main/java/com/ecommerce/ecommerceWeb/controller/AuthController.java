package com.ecommerce.ecommerceWeb.controller;

import com.ecommerce.ecommerceWeb.model.request.LoginRequest;
import com.ecommerce.ecommerceWeb.model.request.SignupRequest;
import com.ecommerce.ecommerceWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseEntity response = userService.signIn(loginRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/signup")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    ResponseEntity response = userService.signUp(signUpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

        @PostMapping("/password")
    public ResponseEntity<String> setPassword(String hash, String password, String rePassword){
        String response = userService.setPassword(hash, password, rePassword);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(String pin){
        String response = userService.resetPassword(pin);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
