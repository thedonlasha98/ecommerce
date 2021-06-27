package com.ecommerce.web.service;

import com.ecommerce.web.ropository.ProductRepository;
import com.ecommerce.web.service.projection.ExcelProjection;
import com.ecommerce.web.service.projection.ExcelTransProjection;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelReportServiceImpl implements ExcelReportService{

    private final ProductRepository productRepository;

    public ExcelReportServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ExcelProjection getExcelData() {

        ExcelProjection excelProjection = productRepository.getExelQueryData(LocalDate.now());
        return excelProjection;
    }

    @Override
    public List<ExcelTransProjection> getTransactions() {

        List<ExcelTransProjection> transactions = productRepository.getTransactionToday(LocalDate.now());
        return transactions;
    }
}
