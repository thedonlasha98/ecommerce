package com.ecommerce.ecommerceWeb.controller;

import com.ecommerce.ecommerceWeb.configuration.jwt.JwtUtils;
import com.ecommerce.ecommerceWeb.facade.EcommerceFacade;
import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.model.UserDto;
import com.ecommerce.ecommerceWeb.service.MailService;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecom")
public class EcommerceController {

    @Autowired
    private EcommerceFacade ecommerceFacade;

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(ProductDto productDto){
        String response = ecommerceFacade.addProducts(productDto);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
