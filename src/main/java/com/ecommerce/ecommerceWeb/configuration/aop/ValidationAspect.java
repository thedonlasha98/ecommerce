package com.ecommerce.ecommerceWeb.configuration.aop;

import com.ecommerce.ecommerceWeb.configuration.jwt.AuthTokenFilter;
import com.ecommerce.ecommerceWeb.domain.WebRequest;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.ropository.WebRequestRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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

    @Before("execution(* com.ecommerce.ecommerceWeb.controller.ProductController.*(..)) && args(request,productDto)")
    public void validationProductDto(HttpServletRequest request, ProductDto productDto){
        LOGGER.info("user id address: " + request.getRemoteAddr());
        productDto.setUserId(authTokenFilter.getUserId(request));

        setWebRequest(request.getRemoteAddr(),"MAIN");

    }

    @Before("execution(* com.ecommerce.ecommerceWeb.controller.ProductController.*(..)) && args(request,buyProductDto)")
    public void validationBuyProductDto(HttpServletRequest request, BuyProductDto buyProductDto){
        LOGGER.info("user id address: " + request.getRemoteAddr());
        buyProductDto.setUserId(authTokenFilter.getUserId(request));

        setWebRequest(request.getRemoteAddr(),"MAIN");

    }

    @Before("execution(* com.ecommerce.ecommerceWeb.controller.ProductController.*(..)) && args(request,userId,id)")
    public void validationUserId(HttpServletRequest request, Long userId, Long id){

        LOGGER.info("user id address: " + request.getRemoteAddr());
            userId = authTokenFilter.getUserId(request);

        setWebRequest(request.getRemoteAddr(),"MAIN");

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