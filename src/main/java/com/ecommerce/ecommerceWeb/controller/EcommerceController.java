package com.ecommerce.ecommerceWeb.controller;

import com.ecommerce.ecommerceWeb.facade.EcommerceFacade;
import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.model.UserDto;
import com.ecommerce.ecommerceWeb.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ecom")
public class EcommerceController {

    @Autowired
    private EcommerceFacade ecommerceFacade;

    @PostMapping("/register/")
    public ResponseEntity<Void> registerUser(UserDto userDto){
        ecommerceFacade.registerUser(userDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password/")
    public ResponseEntity<Void> setPassword(String password, String rePassword){
        ecommerceFacade.setPassword(password, rePassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
