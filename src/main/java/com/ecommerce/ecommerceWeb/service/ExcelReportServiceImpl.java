package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.ropository.ProductRepository;
import com.ecommerce.ecommerceWeb.service.projection.ExcelProjection;
import com.ecommerce.ecommerceWeb.service.projection.ExcelTransProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelReportServiceImpl implements ExcelReportService{

    @Autowired
    private ProductRepository productRepository;

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
