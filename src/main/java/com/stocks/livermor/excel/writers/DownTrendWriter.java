package com.stocks.livermor.excel.writers;

import com.stocks.livermor.entity.Record;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import static com.stocks.livermor.excel.Styles.cellStyleWithBorder;
import static com.stocks.livermor.excel.Styles.defaultFont;

public class DownTrendWriter {
    public static void write(Workbook workbook, Cell cell, Record record) {
        RichTextString richTextString = new XSSFRichTextString(record.getPrice() + "");
        richTextString.applyFont(defaultFont(workbook, Font.COLOR_RED));
        cell.setCellValue(richTextString);

        if (record.isPivotPoint()) {
            cell.setCellStyle(cellStyleWithBorder(workbook, IndexedColors.BLACK.index));
        }
    }

}
