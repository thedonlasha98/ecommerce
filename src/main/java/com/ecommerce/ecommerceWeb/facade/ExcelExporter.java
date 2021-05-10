package com.ecommerce.ecommerceWeb.facade;

import com.ecommerce.ecommerceWeb.service.MailService;
import com.ecommerce.ecommerceWeb.service.projection.ExcelProjection;
import com.ecommerce.ecommerceWeb.service.projection.ExcelTransProjection;
import com.sun.istack.ByteArrayDataSource;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Properties;

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

        createCell(row, 6, "product", style);
        createCell(row, 7, "productName", style);
        createCell(row, 8, "amount", style);
        createCell(row, 9, "buyer's email", style);
        createCell(row, 10, "buyer's pin", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else {
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
            createCell(row, columnCount++, excelData.getProdCount().toString(), style);
            createCell(row, columnCount++, excelData.getUserCount(), style);

        for (ExcelTransProjection trans : transactionsList) {
            Row transRow = sheet.createRow(rowCount++);
            columnCount = 6;

            createCell(transRow, columnCount++, trans.getProduct(), style);
            createCell(transRow, columnCount++, trans.getProductName(), style);
            createCell(transRow, columnCount++, trans.getAmount(), style);
            createCell(transRow, columnCount++, trans.getBuyerEmail(), style);
            createCell(transRow, columnCount++, trans.getBuyerPin(), style);

        }

    }

    @SneakyThrows
    public void export() throws IOException {

        writeHeaderLine();
        writeDataLines();

        FileOutputStream out = new FileOutputStream(new File("report.xlsx"));
        workbook.write(out);
        out.close();
        System.out.println("report.xlsx written successfully on disk.");
    }
}