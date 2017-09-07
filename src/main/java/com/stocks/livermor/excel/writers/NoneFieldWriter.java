package com.stocks.livermor.excel.writers;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.Signal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import static com.stocks.livermor.entity.Signal.DOWN_TREND_IS_OVER;
import static com.stocks.livermor.entity.Signal.UPPER_TREND_IS_OVER;
import static com.stocks.livermor.excel.Styles.cellStyleBackgroundColor;
import static com.stocks.livermor.excel.Styles.defaultCellStyle;
import static com.stocks.livermor.excel.Styles.defaultFont;

public class NoneFieldWriter {
    public static void write(Workbook workbook, Cell cell, Record record) {
        RichTextString richTextString = new XSSFRichTextString(record.getPrice() + "");
        richTextString.applyFont(defaultFont(workbook, IndexedColors.GREY_40_PERCENT.index));
        cell.setCellValue(richTextString);

        cell.setCellStyle(defaultCellStyle(workbook));

        final Signal signal = record.getSignal();
        if (signal == DOWN_TREND_IS_OVER || signal == UPPER_TREND_IS_OVER) {
            cell.setCellStyle(cellStyleBackgroundColor(workbook, IndexedColors.LIGHT_GREEN.index));
        }
    }

}
