package com.ecommerce.web.service;

import com.ecommerce.web.service.projection.ExcelProjection;
import com.ecommerce.web.service.projection.ExcelTransProjection;

import java.util.List;

public interface ExcelReportService {

    ExcelProjection getExcelData();

    List<ExcelTransProjection> getTransactions();
}
