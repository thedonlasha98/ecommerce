package com.ecommerce.web.configuration.aop;

import com.ecommerce.web.domain.WebRequest;
import com.ecommerce.web.jwt.config.AuthTokenFilter;
import com.ecommerce.web.model.BuyProductDto;
import com.ecommerce.web.model.ProductDto;
import com.ecommerce.web.ropository.WebRequestRepository;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;


@Aspect
@Component
public class ValidationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    private final AuthTokenFilter authTokenFilter;

    private final WebRequestRepository webRequestRepository;

    public ValidationAspect(AuthTokenFilter authTokenFilter, WebRequestRepository webRequestRepository) {
        this.authTokenFilter = authTokenFilter;
        this.webRequestRepository = webRequestRepository;
    }

    @SneakyThrows
    @Before("execution(* com.ecommerce.web.controller.*.*(..))")
    public void preLogger(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        String args = Arrays.toString(joinPoint.getArgs());

        if (request != null) {
            LOGGER.info("=============Received Http Request============");
            LOGGER.info("Http Method = {}", request.getMethod());
            LOGGER.info("URI = {}", request.getRequestURI());
            LOGGER.info("CLASS Method = {}", joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName());
            LOGGER.info("ARGS = {}", args);
            LOGGER.info("REQUESTER IP = {}", request.getRemoteAddr());
        }
    }

    @SneakyThrows
    @Around("execution(* com.ecommerce.web.controller.*.*(..))")
    public ResponseEntity validationProduct(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        LOGGER.info("****LoggingAspect.logAroundAllMethods() : " + joinPoint.getSignature().getName() + ": Before Method Execution");
        setWebRequest(request.getRemoteAddr());
        Object[] args = joinPoint.getArgs();
        ProductDto productDto;
        Object result;

        switch (joinPoint.getSignature().getName()) {
            case "closeProduct":
            case "activateProduct":
            case "deleteProduct":
                args[1] = authTokenFilter.getUserId(request);
                result = joinPoint.proceed(args);
                break;
            case "modifyProduct":
            case "addProduct":
                productDto = (ProductDto) args[0];
                productDto.setUserId(authTokenFilter.getUserId(request));
                result = joinPoint.proceed(new ProductDto[]{productDto});
                break;
            case "buyProduct":
                BuyProductDto buyProductDto = (BuyProductDto) args[0];
                buyProductDto.setUserId(authTokenFilter.getUserId(request));
                result = joinPoint.proceed(new BuyProductDto[]{buyProductDto});
                break;
            case "getProducts":
                result = joinPoint.proceed();
                break;
            default:
                LOGGER.info("exception");
                result = joinPoint.proceed();
        }

        return (ResponseEntity) result;
    }

    private void setWebRequest(String ipAddress) {
        WebRequest webRequest = webRequestRepository.findByIpAddressAndInpSysdate(ipAddress, LocalDate.now());

        if (webRequest == null) {
            WebRequest wr = new WebRequest();
            wr.setIpAddress(ipAddress);
            wr.setRequestForm("MAIN");
            wr.setInpSysdate(LocalDate.now());

            webRequestRepository.save(wr);

        }
    }
}