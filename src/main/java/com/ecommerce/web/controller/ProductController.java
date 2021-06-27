package com.ecommerce.web.controller;

import com.ecommerce.web.facade.EcommerceFacade;
import com.ecommerce.web.model.BuyProductDto;
import com.ecommerce.web.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Long userId) {
        ecommerceFacade.deleteProduct(id, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> modifyProduct(ProductDto productDto) {
        String response = ecommerceFacade.modifyProduct(productDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
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
