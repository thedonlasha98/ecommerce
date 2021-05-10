package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.service.projection.ExcelProjection;
import com.ecommerce.ecommerceWeb.service.projection.ExcelTransProjection;

import java.util.List;

public interface ExcelReportService {

    ExcelProjection getExcelData();

    List<ExcelTransProjection> getTransactions();
}
