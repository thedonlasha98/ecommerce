package com.ecommerce.ecommerceWeb.facade;

import com.ecommerce.ecommerceWeb.model.UserDto;
import com.ecommerce.ecommerceWeb.service.EcommerceService;
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
    EcommerceService ecommerceService;

    public void registerUser(UserDto userDto){
        ecommerceService.registerUser(userDto);
    }

    public void setPassword(String password, String rePassword){
        ecommerceService.setPassword(password,rePassword);
    }
}
