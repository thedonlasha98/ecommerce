package com.ecommerce.ecommerceWeb.configuration.aop;

import com.ecommerce.ecommerceWeb.configuration.jwt.AuthTokenFilter;
import com.ecommerce.ecommerceWeb.domain.WebRequest;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.ropository.WebRequestRepository;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;


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
    @Around("execution(* com.ecommerce.ecommerceWeb.controller.ProductController.*(..))")
    public void validationProductDto(ProceedingJoinPoint joinPoint) {


        LOGGER.info("****LoggingAspect.logAroundAllMethods() : " + joinPoint.getSignature().getName() + ": Before Method Execution");

        Object[] args = joinPoint.getArgs();
        ProductDto productDto;
        switch (joinPoint.getSignature().getName()){
            case "closeProduct" :
                LOGGER.info("close is being called");
                setWebRequest(request.getRemoteAddr(),"closeProduct");
                args[1] = authTokenFilter.getUserId(request);
                joinPoint.proceed(args);
                break;
            case "activateProduct" :
                LOGGER.info("activate is being called");
                setWebRequest(request.getRemoteAddr(),"activateProduct");
                args[1] = authTokenFilter.getUserId(request);
                joinPoint.proceed(args);
                break;
            case "deleteProduct" :
                LOGGER.info("delete is being called");
                setWebRequest(request.getRemoteAddr(),"deleteProduct");
                args[1] = authTokenFilter.getUserId(request);
                joinPoint.proceed(args);
                break;
            case "modifyProduct" :
                LOGGER.info("modify is being called");
                setWebRequest(request.getRemoteAddr(),"modifyProduct");
                productDto = (ProductDto) args[0];
                productDto.setUserId(authTokenFilter.getUserId(request));
                joinPoint.proceed(new ProductDto[]{productDto});
                break;
            case "addProduct" :
                LOGGER.info("add is being called");
                setWebRequest(request.getRemoteAddr(),"addProduct");
                productDto = (ProductDto) args[0];
                productDto.setUserId(authTokenFilter.getUserId(request));
                joinPoint.proceed(new ProductDto[]{productDto});
                break;
            case "buyProduct" :
                LOGGER.info("buy is being called");
                setWebRequest(request.getRemoteAddr(),"buyProduct");
                BuyProductDto buyProductDto = (BuyProductDto) args[0];
                buyProductDto.setUserId(authTokenFilter.getUserId(request));
                joinPoint.proceed(new BuyProductDto[]{buyProductDto});
                break;
            case "getProducts" :
                LOGGER.info("getProducts is being called");
                setWebRequest(request.getRemoteAddr(),"getProducts");
                break;
            default: LOGGER.info("exception");
        }
    }

    private void setWebRequest(String ipAddress, String form){
        WebRequest webRequest = webRequestRepository.findByIpAddressAndRequestFormAndTruncInpSysdate(ipAddress,form,LocalDate.now());

        if (webRequest == null){
            WebRequest wr = new WebRequest();
            wr.setIpAddress(ipAddress);
            wr.setRequestForm(form);
            wr.setInpSysdate(LocalDateTime.now());
            
            webRequestRepository.save(wr);

        }
    }
}