package com.ecommerce.web.facade;

import com.ecommerce.web.domain.Product;
import com.ecommerce.web.exception.ErrorMessage;
import com.ecommerce.web.exception.GeneralException;
import com.ecommerce.web.model.BuyProductDto;
import com.ecommerce.web.model.MailDto;
import com.ecommerce.web.model.ProductDto;
import com.ecommerce.web.service.ExcelReportService;
import com.ecommerce.web.service.MailService;
import com.ecommerce.web.service.ProductService;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EcommerceFacade {

    private final ProductService productService;

    private final ExcelReportService excelReportService;

    private final MailService mailService;

    public EcommerceFacade(ProductService productService, ExcelReportService excelReportService, MailService mailService) {
        this.productService = productService;
        this.excelReportService = excelReportService;
        this.mailService = mailService;
    }

    public String addProduct(ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    public void activateProduct(Long id, Long userId) {
        productService.activateProduct(id, userId);
    }

    public void closeProduct(Long id, Long userId) {
        productService.closeProduct(id, userId);
    }

    public void deleteProduct(Long id, Long userId) {
        productService.deleteProduct(id, userId);
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
        boolean validCard = validateCard(buyProductDto.getCardNo(), buyProductDto.getCvv(), buyProductDto.getExpDate());
        if (validCard) {
            productService.buyProduct(buyProductDto);
        } else {
            throw new GeneralException(ErrorMessage.INCORRECT_CARD_CREDENTIALS);
        }
    }

    @SneakyThrows
    @Scheduled(cron = "0 30 2 * * *")
    public void excelReportScheduler() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Date now = new Date();
        String strDate = sdf.format(now);
        ExcelExporter excelExporter = new ExcelExporter(excelReportService.getExcelData(), excelReportService.getTransactions());
        excelExporter.export();

        MailDto mailDto = MailDto.builder()
                .email("bolgalasha@gmail.com")
                .subject("Daily Report")
                .body("Daily Report")
                .fileAttach("report.xlsx")
                .build();

        mailService.sendMailWithAttachment(mailDto);
    }

    public boolean validateCard(Long cardNo, int cvv, String expDate) {
        return String.valueOf(cardNo).length() == 16 &&
                String.valueOf(cvv).length() == 3 &&
                expDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }
}
