package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.domain.Account;
import com.ecommerce.ecommerceWeb.domain.Product;
import com.ecommerce.ecommerceWeb.domain.ProductHist;
import com.ecommerce.ecommerceWeb.domain.ProductsTransaction;
import com.ecommerce.ecommerceWeb.exception.ErrorCode;
import com.ecommerce.ecommerceWeb.exception.GeneralException;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.ropository.AccountRepository;
import com.ecommerce.ecommerceWeb.ropository.ProductHistRepository;
import com.ecommerce.ecommerceWeb.ropository.ProductRepository;
import com.ecommerce.ecommerceWeb.ropository.ProductsTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductHistRepository productHistRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductsTransactionRepository productsTransactionRepository;

    @Transactional
    @Override
    public String addProduct(ProductDto productDto) {
        String result = "გადაამოწმეთ არსებული პროდუქტები!";
        Product productExt = productRepository.findByProductAndUserId(productDto.getProduct(), productDto.getUserId());
        if (productExt == null) {
            Product product = new Product();
            product.setUserId(productDto.getUserId());
            product.setProduct(productDto.getProduct());
            product.setPhoto(productDto.getPhoto());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setStatus("A");
            product = productRepository.save(product);
            //create log
            createLog(product.getId(), productDto.getUserId(), productDto.getProduct(), productDto.getProductName(), productDto.getPhoto(), productDto.getPrice(), productDto.getQuantity(), "A", "ADD");

            result = "პროდუქტი წარმატებით დარეგისტრირდა!";
        } else if (productExt.getStatus().equals("A")) {
            productExt.setQuantity(productExt.getQuantity() + productDto.getQuantity());
            productRepository.save(productExt);
            //create log
            createLog(productDto.getProductId(), productDto.getUserId(), productDto.getProduct(), productDto.getProductName(), productDto.getPhoto(), productDto.getPrice(), productDto.getQuantity(), "A", "ADD_CURRENT");

            result = "არსებული პროდუქტის რაოდენობა გაიზარდა " + productDto.getQuantity() + "-ით!";
        }
        return result;
    }

    @Transactional
    @Override
    public String modifyProduct(ProductDto productDto) {
        Product product = productRepository.getProductById(productDto.getProductId());
        if (product != null && product.getUserId().equals(productDto.getUserId())) {
            product.setProduct(productDto.getProduct());
            product.setPhoto(productDto.getPhoto());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setStatus("A");
            productRepository.save(product);
            //create log
            createLog(product.getId(), productDto.getUserId(), product.getProduct(), productDto.getProductName(), product.getPhoto(), product.getPrice(), product.getQuantity(), "A", "MODIFY");

            return "პროდუქტი რედაქტირებულია!";
        } else {
            throw new GeneralException(ErrorCode.PRODUCTS_USER_AND_USER_NOT_EQUALS);
        }
    }

    @Transactional
    @Override
    public void deleteProducts(Long id, Long userId) {
        Product product = productRepository.getProductById(id);
        if (product.getUserId().equals(userId)) {
            productRepository.deleteById(id);
            product.setStatus("D");
            productRepository.save(product);
            //create log
            createLog(product.getId(), userId, product.getProduct(), product.getProductName(), product.getPhoto(), product.getPrice(), product.getQuantity(), "D", "DELETE");
        } else {
            throw new GeneralException(ErrorCode.PRODUCTS_USER_AND_USER_NOT_EQUALS);
        }
    }

    @Transactional
    @Override
    public void closeProducts(Long id, Long userId) {
        Product product = productRepository.getProductById(id);
        if (product.getUserId().equals(userId)) {
            product.setStatus("C");
            productRepository.save(product);
            //create log
            createLog(product.getId(), userId, product.getProduct(), product.getProductName(), product.getPhoto(), product.getPrice(), product.getQuantity(), "C", "CLOSE");

        } else {
            throw new GeneralException(ErrorCode.PRODUCTS_USER_AND_USER_NOT_EQUALS);
        }
    }

    @Transactional
    @Override
    public void activateProducts(Long id, Long userId) {
        Product product = productRepository.findByIdAndUserId(id, userId);
        if (product.getUserId().equals(userId)) {
            product.setStatus("A");
            productRepository.save(product);
            //create log
            createLog(product.getId(), userId, product.getProduct(), product.getProductName(), product.getPhoto(), product.getPrice(), product.getQuantity(), "A", "ACTIVATE");
        } else {
            throw new GeneralException(ErrorCode.PRODUCTS_USER_AND_USER_NOT_EQUALS);
        }
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAllByStatusOrderByIdDesc("A");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buyProduct(BuyProductDto buyProductDto) {
        Product product = productRepository.getProductById(buyProductDto.getProductId());
        if (product.getQuantity() >= buyProductDto.getQuantity()) {
            Account ownersAccount = accountRepository.getAccountByUserId(product.getUserId());
            Account buyersAccount = accountRepository.getAccountByUserId(buyProductDto.getUserId());

            double amount = product.getPrice() * buyProductDto.getQuantity();
            double comAmount = amount * 10 / 100;
            double netAmount = amount - comAmount;

            if (buyersAccount.getBalance() >= amount) {

                ProductsTransaction productsTransaction = new ProductsTransaction();
                productsTransaction.setProductId(buyProductDto.getProductId());
                productsTransaction.setFromAcctId(buyersAccount.getId());
                productsTransaction.setToAcctId(ownersAccount.getId());
                productsTransaction.setQuantity(buyProductDto.getQuantity());
                productsTransaction.setAmount(amount);
                productsTransaction.setComAmount(comAmount);
                productsTransaction.setInpSysdate(LocalDateTime.now());

                ownersAccount.setBalance(ownersAccount.getBalance() + netAmount);
                buyersAccount.setBalance(buyersAccount.getBalance() - amount);

                accountRepository.save(buyersAccount);
                accountRepository.save(ownersAccount);
                productsTransactionRepository.save(productsTransaction);

                product.setQuantity(product.getQuantity() - buyProductDto.getQuantity());
                if (product.getQuantity().equals(ZERO)) {
                    product.setStatus("C");
                }
                productRepository.save(product);
            } else {
                throw new GeneralException(ErrorCode.NOT_ENOUGH_BALANCE);
            }
        } else {
            throw new GeneralException(ErrorCode.NOT_ENOUGH_QUANTITY_IN_STOCK);
        }

    }

    @Transactional
    public void createLog(Long productId, Long userId, String product, String productName, byte[] photo, Double price, Long quantity, String status, String event) {
        ProductHist productHist = new ProductHist();
        productHist.setProductId(productId);
        productHist.setUserId(userId);
        productHist.setProduct(product);
        productHist.setProductName(productName);
        productHist.setPhoto(photo);
        productHist.setPrice(price);
        productHist.setQuantity(quantity);
        productHist.setStatus(status);
        productHist.setEvent(event);
        productHist.setInpSysDate(LocalDateTime.now());

        productHistRepository.save(productHist);
    }

}
