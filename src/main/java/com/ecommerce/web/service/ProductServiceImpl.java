package com.ecommerce.web.service;

import com.ecommerce.web.domain.Account;
import com.ecommerce.web.domain.Product;
import com.ecommerce.web.domain.ProductHist;
import com.ecommerce.web.domain.ProductsTransaction;
import com.ecommerce.web.exception.ErrorMessage;
import com.ecommerce.web.exception.GeneralException;
import com.ecommerce.web.model.BuyProductDto;
import com.ecommerce.web.model.ProductDto;
import com.ecommerce.web.ropository.AccountRepository;
import com.ecommerce.web.ropository.ProductHistRepository;
import com.ecommerce.web.ropository.ProductRepository;
import com.ecommerce.web.ropository.ProductsTransactionRepository;
import com.ecommerce.web.utils.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductHistRepository productHistRepository;

    private final AccountRepository accountRepository;

    private final ProductsTransactionRepository productsTransactionRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductHistRepository productHistRepository, AccountRepository accountRepository, ProductsTransactionRepository productsTransactionRepository) {
        this.productRepository = productRepository;
        this.productHistRepository = productHistRepository;
        this.accountRepository = accountRepository;
        this.productsTransactionRepository = productsTransactionRepository;
    }

    public static final String ACTIVE = "A";
    public static final String CLOSED = "C";
    public static final String ADD = "ADD";
    public static final String MODIFY = "MODIFY";
    public static final String ADD_CURRENT = "ADD_CURRENT";
    public static final String DELETE = "DELETE";
    public static final String CLOSE = "CLOSE";
    public static final String ACTIVATE = "ACTIVATE";

    @Override
    public String addProduct(ProductDto productDto) {
        String result = "Check current products!";
        Product productExt = productRepository.findByProductAndUserId(productDto.getProduct(), productDto.getUserId());
        if (productExt == null) {
            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);
            product.setStatus(ACTIVE);
            product = productRepository.save(product);
            /** create log **/
            createLog(product, ADD);

            result = "Product is registered successfully!";
        } else if (productExt.getStatus().equals(ACTIVE)) {
            /** create log **/
            createLog(productExt, ADD_CURRENT);
            /** add current quantity **/
            productExt.setQuantity(productExt.getQuantity() + productDto.getQuantity());
            productRepository.save(productExt);

            result = "Current product quantity Increased by " + productDto.getQuantity() + "!";
        }
        return result;
    }

    @Override
    public String modifyProduct(ProductDto productDto) {
        Product product = productRepository.findByIdAndUserId(productDto.getProductId(), productDto.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorMessage.PRODUCT_NOT_FOUND_WITH_PROVIDED_ID_AND_USER_ID));
            /** create log **/
            createLog(product, MODIFY);
            /** modify **/
            BeanUtils.copyProperties(productDto, product);
            productRepository.save(product);

            return "Product is modify!";
    }

    @Override
    public void deleteProduct(Long id, Long userId) {
        Product product = productRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new GeneralException(ErrorMessage.PRODUCT_NOT_FOUND_WITH_PROVIDED_ID_AND_USER_ID));
            /** create log **/
            createLog(product, DELETE);
            /** delete **/
            productRepository.delete(product);

    }

    @Override
    public void closeProduct(Long id, Long userId) {
        Product product = productRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new GeneralException(ErrorMessage.PRODUCT_NOT_FOUND_WITH_PROVIDED_ID_AND_USER_ID));
            /** create log **/
            createLog(product, CLOSE);
            /** close **/
            product.setStatus(CLOSED);
            productRepository.save(product);
    }

    @Override
    public void activateProduct(Long id, Long userId) {
        Product product = productRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new GeneralException(ErrorMessage.PRODUCT_NOT_FOUND_WITH_PROVIDED_ID_AND_USER_ID));
        /** create log **/
        createLog(product, ACTIVATE);
        /** activate **/
        product.setStatus(ACTIVE);

        productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAllByStatusOrderByIdDesc(ACTIVE);
    }

    @Transactional
    @Override
    public void buyProduct(BuyProductDto buyProductDto) {
        Product product = productRepository.getProductById(buyProductDto.getProductId());
        if (product.getQuantity() >= buyProductDto.getQuantity()) {
            Account ownersAccount = accountRepository.getAccountByUserId(product.getUserId());
            Account buyersAccount = accountRepository.getAccountByUserId(buyProductDto.getUserId());

            BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(buyProductDto.getQuantity()));
            BigDecimal comAmount = amount.multiply(BigDecimal.TEN).divide(Constant.HUNDRED);
            BigDecimal netAmount = amount.subtract(comAmount);

            if (buyersAccount.getBalance().compareTo(amount) >= 0) {

                ProductsTransaction productsTransaction = createTransaction(
                        buyProductDto.getProductId(),
                        buyersAccount.getId(),
                        ownersAccount.getId(),
                        buyProductDto.getQuantity(),
                        amount,
                        comAmount);

                ownersAccount.setBalance(ownersAccount.getBalance().add(netAmount));
                buyersAccount.setBalance(buyersAccount.getBalance().subtract(amount));

                accountRepository.save(buyersAccount);
                accountRepository.save(ownersAccount);
                productsTransactionRepository.save(productsTransaction);

                product.setQuantity(product.getQuantity() - buyProductDto.getQuantity());
                if (product.getQuantity().equals(ZERO)) {
                    product.setStatus(CLOSED);
                }
                productRepository.save(product);
            } else {
                throw new GeneralException(ErrorMessage.NOT_ENOUGH_BALANCE);
            }
        } else {
            throw new GeneralException(ErrorMessage.NOT_ENOUGH_QUANTITY_IN_STOCK);
        }

    }

    public void createLog(Product product, String event) {
        ProductHist productHist = new ProductHist();
        BeanUtils.copyProperties(product, productHist);
        productHist.setProductId(product.getId());
        productHist.setInpSysDate(LocalDateTime.now());
        productHist.setEvent(event);

        productHistRepository.save(productHist);
    }

    public ProductsTransaction createTransaction(long productId, Long buyersAcct, Long ownersAcct, Integer Quantity, BigDecimal amount, BigDecimal comAmount) {
        ProductsTransaction productsTransaction = new ProductsTransaction();
        productsTransaction.setProductId(productId);
        productsTransaction.setFromAcctId(buyersAcct);
        productsTransaction.setToAcctId(ownersAcct);
        productsTransaction.setQuantity(Quantity);
        productsTransaction.setAmount(amount);
        productsTransaction.setComAmount(comAmount);
        productsTransaction.setInpSysdate(LocalDateTime.now());

        return productsTransaction;
    }

}
