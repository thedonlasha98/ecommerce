package com.ecommerce.ecommerceWeb.controller;

import com.ecommerce.ecommerceWeb.configuration.jwt.AuthTokenFilter;
import com.ecommerce.ecommerceWeb.facade.EcommerceFacade;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private EcommerceFacade ecommerceFacade;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(HttpServletRequest request, ProductDto productDto) {
        productDto.setUserId(authTokenFilter.getUserId(request));
        String response = ecommerceFacade.addProducts(productDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateProducts(HttpServletRequest request, @PathVariable Long id) {
        ecommerceFacade.activateProducts(id, authTokenFilter.getUserId(request));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<?> closeProducts(HttpServletRequest request, @PathVariable Long id) {
        ecommerceFacade.closeProducts(id, authTokenFilter.getUserId(request));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProducts(HttpServletRequest request, @PathVariable Long id) {
        ecommerceFacade.deleteProducts(id, authTokenFilter.getUserId(request));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<String> modifyProduct(HttpServletRequest request, ProductDto productDto) {
        productDto.setUserId(authTokenFilter.getUserId(request));
        String response = ecommerceFacade.modifyProduct(productDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> response = ecommerceFacade.getProducts();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(HttpServletRequest request, BuyProductDto buyProductDto){
        buyProductDto.setUserId(authTokenFilter.getUserId(request));
        ecommerceFacade.buyProduct(buyProductDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
