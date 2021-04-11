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
        String result = "გადაამოწმეთ არსებული პროდუქტები!";
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

            result = "პროდუქტი წარმატებით დარეგისტრირდა!";
        }else if(productExt.getStatus().equals("A")){
            productExt.setQuantity(productExt.getQuantity() + productDto.getQuantity());
            productRepository.save(productExt);
            //create log
            ProductHist productHist = new ProductHist();
            productHist.setProductId(productExt.getId());
            productHist.setUserId(productDto.getUserId());
            productHist.setProduct(productDto.getProduct());
            productHist.setPhoto(productDto.getPhoto());
            productHist.setPrice(productDto.getPrice());
            productHist.setQuantity(productDto.getQuantity());
            productHist.setStatus("A");
            productHist.setEvent("ADD_CURRENT");
            productHist.setInpSysDate(LocalDateTime.now());

            productHistRepository.save(productHist);

            result = "არსებული პროდუქტის რაოდენობა გაიზარდა " + productDto.getQuantity() + "-ით!";
        }
            return result;
    }

    public String modifyProduct(ProductDto productDto){
        String result = "შეცდომა!";
        Product product = productRepository.findByProductAndUserId(productDto.getProduct(),productDto.getUserId());
        if (product == null){
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
            productHist.setEvent("MODIFY");
            productHist.setInpSysDate(LocalDateTime.now());

            productHistRepository.save(productHist);

            result = "პროდუქტი რედაქტირებულია!";
        }
            return result;
    }

    public void deleteProducts(Long id, Long userId){
        Product product = productRepository.findByIdAndUserId(id, userId);
        product.setStatus("D");
        productRepository.save(product);
        //create log
        ProductHist productHist = new ProductHist();
        productHist.setProductId(product.getId());
        productHist.setUserId(userId);
        productHist.setProduct(product.getProduct());
        productHist.setPhoto(product.getPhoto());
        productHist.setPrice(product.getPrice());
        productHist.setQuantity(product.getQuantity());
        productHist.setStatus("D");
        productHist.setEvent("DELETE");
        productHist.setInpSysDate(LocalDateTime.now());

        productHistRepository.save(productHist);
    }

    public void closeProducts(Long id, Long userId){
        Product product = productRepository.findByIdAndUserId(id, userId);
        product.setStatus("C");
        productRepository.save(product);
        //create log
        ProductHist productHist = new ProductHist();
        productHist.setProductId(product.getId());
        productHist.setUserId(userId);
        productHist.setProduct(product.getProduct());
        productHist.setPhoto(product.getPhoto());
        productHist.setPrice(product.getPrice());
        productHist.setQuantity(product.getQuantity());
        productHist.setStatus("C");
        productHist.setEvent("CLOSE");
        productHist.setInpSysDate(LocalDateTime.now());

        productHistRepository.save(productHist);
    }

    public void activateProducts(Long id, Long userId){
        Product product = productRepository.findByIdAndUserId(id, userId);
        product.setStatus("A");
        productRepository.save(product);
        //create log
        ProductHist productHist = new ProductHist();
        productHist.setProductId(product.getId());
        productHist.setUserId(userId);
        productHist.setProduct(product.getProduct());
        productHist.setPhoto(product.getPhoto());
        productHist.setPrice(product.getPrice());
        productHist.setQuantity(product.getQuantity());
        productHist.setStatus("A");
        productHist.setEvent("ACTIVATE");
        productHist.setInpSysDate(LocalDateTime.now());

        productHistRepository.save(productHist);
    }

}
