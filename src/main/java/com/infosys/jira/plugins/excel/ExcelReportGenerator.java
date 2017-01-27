package com.infosys.jira.plugins.excel;

import com.infosys.jira.plugins.email.EmailReport;
import com.infosys.jira.plugins.util.PluginConstants;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Raj on 22/01/2017.
 */
public class ExcelReportGenerator {

    private Logger logger = Logger.getLogger(ExcelReportGenerator.class);

    private AtomicInteger counter = new AtomicInteger(0);

    public void generate(Map<String, EmailReport> emailReportMap) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        createHeaderRow(sheet);

        int rowCount = 0;

        for (Map.Entry<String, EmailReport> reportEntry : emailReportMap.entrySet()) {
            Row row = sheet.createRow(++rowCount);
            EmailReport report = reportEntry.getValue();
            writeReport(report, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(PluginConstants.EXCEL_REPORT_PATH)) {
            workbook.write(outputStream);
        }
    }

    private void writeReport(EmailReport report, Row row) {

        Cell cell = row.createCell(0);
        cell.setCellValue(counter.incrementAndGet());

        cell = row.createCell(1);
        cell.setCellValue(report.getUserName());

        cell = row.createCell(2);
        cell.setCellValue(report.getDisplayName());

        cell = row.createCell(3);
        cell.setCellValue(report.getEmailAddress());

        cell = row.createCell(4);
        cell.setCellValue(String.valueOf(report.getBrowser()));
    }

    private void createHeaderRow(Sheet sheet) {

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);

        Cell cellSerialNo = row.createCell(0);
        cellSerialNo.setCellStyle(cellStyle);
        cellSerialNo.setCellValue(PluginConstants.SERIAL_NUMBER);

        Cell cellUserName = row.createCell(1);
        cellUserName.setCellStyle(cellStyle);
        cellUserName.setCellValue(PluginConstants.USER_NAME);

        Cell cellDisplayName = row.createCell(2);
        cellDisplayName.setCellStyle(cellStyle);
        cellDisplayName.setCellValue(PluginConstants.DISPLAY_NAME);

        Cell cellEmailAddress = row.createCell(3);
        cellEmailAddress.setCellStyle(cellStyle);
        cellEmailAddress.setCellValue(PluginConstants.EMAIL_ADDRESS);

        Cell cellBrowser = row.createCell(4);
        cellBrowser.setCellStyle(cellStyle);
        cellBrowser.setCellValue(PluginConstants.BROWSER);

    }
}

