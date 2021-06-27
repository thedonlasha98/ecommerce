package com.ecommerce.web.facade;

import com.ecommerce.web.service.projection.ExcelProjection;
import com.ecommerce.web.service.projection.ExcelTransProjection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private ExcelProjection excelData;
    private List<ExcelTransProjection> transactionsList;

    public ExcelExporter(ExcelProjection excelData, List<ExcelTransProjection> transactionsList) {
        this.excelData = excelData;
        this.transactionsList = transactionsList;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("report");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Transaction count", style);
        createCell(row, 1, "Sum Amount", style);
        createCell(row, 2, "Commis income", style);
        createCell(row, 3, "Add Product count", style);
        createCell(row, 4, "user count", style);
        createCell(row, 5, "request count", style);

        createCell(row, 7, "product", style);
        createCell(row, 8, "productName", style);
        createCell(row, 9, "amount", style);
        createCell(row, 10, "buyer's email", style);
        createCell(row, 11, "buyer's pin", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);


        Row row = sheet.createRow(rowCount);
        int columnCount = 0;

        createCell(row, columnCount++, excelData.getTransCount(), style);
        createCell(row, columnCount++, excelData.getSumAmount(), style);
        createCell(row, columnCount++, excelData.getComAmount(), style);
        createCell(row, columnCount++, excelData.getProdCount(), style);
        createCell(row, columnCount++, excelData.getUserCount(), style);
        createCell(row, columnCount++, excelData.getRequestCount(), style);

        for (ExcelTransProjection trans : transactionsList) {
            Row transRow = sheet.createRow(rowCount++);
            columnCount = 7;

            createCell(transRow, columnCount++, trans.getProduct(), style);
            createCell(transRow, columnCount++, trans.getProductName(), style);
            createCell(transRow, columnCount++, trans.getAmount(), style);
            createCell(transRow, columnCount++, trans.getBuyerEmail(), style);
            createCell(transRow, columnCount++, trans.getBuyerPin(), style);

        }

    }

    public void export() {
        writeHeaderLine();
        writeDataLines();

        try (FileOutputStream out = new FileOutputStream(new File("report.xlsx"))) {
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("report.xlsx written successfully on disk.");
    }
}