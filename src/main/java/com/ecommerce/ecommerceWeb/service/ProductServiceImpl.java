package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.domain.Product;
import com.ecommerce.ecommerceWeb.domain.ProductHist;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.ropository.ProductHistRepository;
import com.ecommerce.ecommerceWeb.ropository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductHistRepository productHistRepository;

    @Override
    public String addProduct(ProductDto productDto){
        Product productExt = productRepository.findByProductAndUserId(productDto.getProduct(),productDto.getUserId());
        if (productExt == null){
            Product product = new Product();
            product.setUserId(productDto.getUserId());
            product.setProduct(productDto.getProduct());
            product.setPhoto(productDto.getPhoto());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setStatus("A");
            productRepository.save(product);
            //create log
            ProductHist productHist = new ProductHist();
            productHist.setProductId(product.getId());
            productHist.setUserId(productDto.getUserId());
            productHist.setProduct(productDto.getProduct());
            productHist.setPhoto(productDto.getPhoto());
            productHist.setPrice(productDto.getPrice());
            productHist.setQuantity(productDto.getQuantity());
            productHist.setStatus("A");
            productHist.setEvent("ADD");
            productHist.setInpSysDate(LocalDateTime.now());

            productHistRepository.save(productHist);
        }
        return "პროდუქტი წარმატებით დარეგისტრირდა";
    }
}
