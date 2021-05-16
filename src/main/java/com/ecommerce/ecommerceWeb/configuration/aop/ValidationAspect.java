package com.ecommerce.ecommerceWeb.configuration.aop;

import com.ecommerce.ecommerceWeb.configuration.jwt.AuthTokenFilter;
import com.ecommerce.ecommerceWeb.domain.WebRequest;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.ropository.WebRequestRepository;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;


@Aspect
@Component
public class ValidationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private WebRequestRepository webRequestRepository;

    @Autowired
    private HttpServletRequest request;

    @SneakyThrows
    @Around("execution(* com.ecommerce.ecommerceWeb.controller.*.*(..))")
    public ResponseEntity validationProduct(ProceedingJoinPoint joinPoint) {

        LOGGER.info("****LoggingAspect.logAroundAllMethods() : " + joinPoint.getSignature().getName() + ": Before Method Execution");
        setWebRequest(request.getRemoteAddr());
        Object[] args = joinPoint.getArgs();
        ProductDto productDto;
        Object result;

        switch (joinPoint.getSignature().getName()){
            case "closeProduct" :
                LOGGER.info("close is being called");
                args[1] = authTokenFilter.getUserId(request);
                result = joinPoint.proceed(args);
                break;
            case "activateProduct" :
                LOGGER.info("activate is being called");
                args[1] = authTokenFilter.getUserId(request);
                result = joinPoint.proceed(args);
                break;
            case "deleteProduct" :
                LOGGER.info("delete is being called");
                args[1] = authTokenFilter.getUserId(request);
                result = joinPoint.proceed(args);
                break;
            case "modifyProduct" :
                LOGGER.info("modify is being called");
                productDto = (ProductDto) args[0];
                productDto.setUserId(authTokenFilter.getUserId(request));
                result = joinPoint.proceed(new ProductDto[]{productDto});
                break;
            case "addProduct" :
                LOGGER.info("add is being called");
                productDto = (ProductDto) args[0];
                productDto.setUserId(authTokenFilter.getUserId(request));
                result = joinPoint.proceed(new ProductDto[]{productDto});
                break;
            case "buyProduct" :
                LOGGER.info("buy is being called");
                BuyProductDto buyProductDto = (BuyProductDto) args[0];
                buyProductDto.setUserId(authTokenFilter.getUserId(request));
                result = joinPoint.proceed(new BuyProductDto[]{buyProductDto});
                break;
            case "getProducts" :
                LOGGER.info("getProducts is being called");
                result = joinPoint.proceed();
                break;
            default:
                LOGGER.info("exception");
                result = joinPoint.proceed();
        }

        return (ResponseEntity) result;
    }

    private void setWebRequest(String ipAddress){
        WebRequest webRequest = webRequestRepository.findByIpAddressAndInpSysdate(ipAddress,LocalDate.now());

        if (webRequest == null){
            WebRequest wr = new WebRequest();
            wr.setIpAddress(ipAddress);
            wr.setRequestForm("MAIN");
            wr.setInpSysdate(LocalDate.now());
            
            webRequestRepository.save(wr);

        }
    }
}