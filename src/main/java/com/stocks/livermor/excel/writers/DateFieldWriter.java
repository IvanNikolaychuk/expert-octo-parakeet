package com.stocks.livermor.excel.writers;

import com.stocks.livermor.entity.Record;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.stocks.livermor.excel.Styles.defaultCellStyle;
import static com.stocks.livermor.excel.Styles.defaultFont;

public class DateFieldWriter {
    public static void write(Workbook workbook, Cell cell, Record record) {
        RichTextString richTextString = new XSSFRichTextString(date(record.getDate()));
        richTextString.applyFont(defaultFont(workbook));
        cell.setCellStyle(defaultCellStyle(workbook));
        cell.setCellValue(richTextString);
    }

    private static String date(Date date) {
        return new SimpleDateFormat("dd.MM").format(date);
    }
}
