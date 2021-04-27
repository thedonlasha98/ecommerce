package com.ecommerce.ecommerceWeb.facade;

import com.ecommerce.ecommerceWeb.configuration.jwt.JwtUtils;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.model.UserDto;
import com.ecommerce.ecommerceWeb.service.ProductService;
import com.ecommerce.ecommerceWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * EcommerceFacade
 *
 * @blame Android Team
 */
@Component
public class EcommerceFacade {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired JwtUtils jwtUtils;

//    public void registerUser(UserDto userDto) {
//        userService.registerUser(userDto);
//    }
//
//    public void setPassword(String password, String rePassword) {
//        userService.setPassword(password, rePassword);
//    }
//
//    public void authorization(String email, String password) {
//        userService.authorization(email, password);
//    }
//
//    public String resetPassword(String pin) {
//        return userService.resetPassword(pin);
//    }

    public String addProducts(ProductDto productDto){
        productDto.setUserId(jwtUtils.getUserId());
        return productService.addProduct(productDto);
    }

}
