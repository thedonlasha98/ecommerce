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
            createLog(productDto.getProductId(),productDto.getUserId(),productDto.getProduct(),productDto.getPhoto(),productDto.getPrice(),productDto.getQuantity(),"A","ADD");

            result = "პროდუქტი წარმატებით დარეგისტრირდა!";
        }else if(productExt.getStatus().equals("A")){
            productExt.setQuantity(productExt.getQuantity() + productDto.getQuantity());
            productRepository.save(productExt);
            //create log
            createLog(productDto.getProductId(),productDto.getUserId(),productDto.getProduct(),productDto.getPhoto(),productDto.getPrice(),productDto.getQuantity(),"A","ADD_CURRENT");

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
            createLog(product.getId(),productDto.getUserId(),product.getProduct(),product.getPhoto(),product.getPrice(),product.getQuantity(),"A","MODIFY");

            result = "პროდუქტი რედაქტირებულია!";
        }
            return result;
    }

    public void deleteProducts(Long id, Long userId){
        Product product = productRepository.findByIdAndUserId(id, userId);
        productRepository.deleteById(id);
        product.setStatus("D");
        productRepository.save(product);
        //create log
        createLog(product.getId(),userId,product.getProduct(),product.getPhoto(),product.getPrice(),product.getQuantity(),"D","DELETE");
    }

    public void closeProducts(Long id, Long userId){
        Product product = productRepository.findByIdAndUserId(id, userId);
        product.setStatus("C");
        productRepository.save(product);
        //create log
        createLog(product.getId(),userId,product.getProduct(),product.getPhoto(),product.getPrice(),product.getQuantity(),"C","CLOSE");
    }

    public void activateProducts(Long id, Long userId){
        Product product = productRepository.findByIdAndUserId(id, userId);
        product.setStatus("A");
        productRepository.save(product);
        //create log
        createLog(product.getId(),userId,product.getProduct(),product.getPhoto(),product.getPrice(),product.getQuantity(),"A","ACTIVATE");
    }

    public void createLog(Long productId, Long userId, String product, byte[] photo, Double price, Long quantity, String status, String event){
        ProductHist productHist = new ProductHist();
        productHist.setProductId(productId);
        productHist.setUserId(userId);
        productHist.setProduct(product);
        productHist.setPhoto(photo);
        productHist.setPrice(price);
        productHist.setQuantity(quantity);
        productHist.setStatus(status);
        productHist.setEvent(event);
        productHist.setInpSysDate(LocalDateTime.now());

        productHistRepository.save(productHist);
    }

}
