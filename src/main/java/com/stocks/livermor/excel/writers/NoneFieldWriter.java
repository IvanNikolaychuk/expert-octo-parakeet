package com.stocks.livermor.excel.writers;

import com.stocks.livermor.entity.Record;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import static com.stocks.livermor.excel.Styles.defaultCellStyle;
import static com.stocks.livermor.excel.Styles.defaultFont;

public class NoneFieldWriter {
    public static void write(Workbook workbook, Cell cell, Record record) {
        RichTextString richTextString = new XSSFRichTextString(record.getPrice() + "");
        richTextString.applyFont(defaultFont(workbook, IndexedColors.GREY_40_PERCENT.index));
        cell.setCellStyle(defaultCellStyle(workbook));
        cell.setCellValue(richTextString);
    }

}
