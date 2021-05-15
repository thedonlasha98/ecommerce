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

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(ProductDto productDto) {
        String response = ecommerceFacade.addProduct(productDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateProduct(@PathVariable Long id, Long userId) {
        ecommerceFacade.activateProduct(id, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<?> closeProduct(@PathVariable Long id, Long userId) {
        ecommerceFacade.closeProduct(id, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Long userId) {
        ecommerceFacade.deleteProduct(id, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyProduct(ProductDto productDto) {
        String response = ecommerceFacade.modifyProduct(productDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDto>> getProducts(){

        List<ProductDto> response = ecommerceFacade.getProducts();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(BuyProductDto buyProductDto){
        ecommerceFacade.buyProduct(buyProductDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
