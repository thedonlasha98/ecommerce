package com.ecommerce.ecommerceWeb.facade;

import com.ecommerce.ecommerceWeb.domain.Product;
import com.ecommerce.ecommerceWeb.exception.ErrorCode;
import com.ecommerce.ecommerceWeb.exception.GeneralException;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.service.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EcommerceFacade
 *
 * @blame Android Team
 */
@Component
public class EcommerceFacade {

    @Autowired
    private ProductService productService;

    @Autowired
    private ExcelReportService excelReportService;

    @Autowired
    private MailService mailService;

    public String addProducts(ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    public void activateProducts(Long id, Long userId) {
        productService.activateProducts(id, userId);
    }

    public void closeProducts(Long id, Long userId) {
        productService.closeProducts(id, userId);
    }

    public void deleteProducts(Long id, Long userId) {
        productService.deleteProducts(id, userId);
    }

    public String modifyProduct(ProductDto productDto) {
        return productService.modifyProduct(productDto);
    }


    public List<ProductDto> getProducts() {
        List<Product> products = productService.getProducts();

        return products.stream()
                .map(ProductDto::transformProducts)
                .collect(Collectors.toList());
    }

    public void buyProduct(BuyProductDto buyProductDto) {
        if (String.valueOf(buyProductDto.getCardNo()).length() == 16 &&
                String.valueOf(buyProductDto.getCvv()).length() == 3 &&
                buyProductDto.getExpDate().matches("(?:0[1-9]|1[0-2])/[0-9]{2}")
        ) {
            productService.buyProduct(buyProductDto);
        }
        else{
            throw new GeneralException(ErrorCode.INCORRECT_CARD_CREDENTIALS);
        }
    }

    @SneakyThrows
    @Scheduled(cron = "0 0 0 * * *")
    public void fixedRateSch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Date now = new Date();
        String strDate = sdf.format(now);
        ExcelExporter excelExporter = new ExcelExporter(excelReportService.getExcelData(), excelReportService.getTransactions());
        excelExporter.export();
        EmailHelper emailHelper = new EmailHelper();
        emailHelper.sendEmail();
        //mailService.sendMailWithAttachment("thedonlasha@gmail.com","report", "daily report",excelExporter.export());
    }
}
