package com.stocks.livermor.excel.writers;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.Signal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import static com.stocks.livermor.entity.Signal.*;
import static com.stocks.livermor.excel.Styles.*;
import static org.apache.poi.ss.usermodel.IndexedColors.*;

public class NaturalRallyWriter {
    public static void write(Workbook workbook, Cell cell, Record record) {
        RichTextString richTextString = new XSSFRichTextString(record.getPrice() + "");
        richTextString.applyFont(defaultFont(workbook, ROYAL_BLUE.index));
        cell.setCellValue(richTextString);

        if (record.isPivotPoint()) {
            cell.setCellStyle(cellStyleWithBorder(workbook, BLACK.index));
        }

        final Signal signal = record.getSignal();
        if (signal == DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT
                || signal == UPPER_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_UPPER_TREND_POINT) {
            cell.setCellStyle(cellStyleBackgroundColor(workbook, LIGHT_GREEN.index));
        }
        if (signal == DOWN_TREND_IS_OVER_BECAUSE_LAST_DOWN_PIVOT_POINT_IS_BROKEN_WEAK
                || signal == UPPER_TREND_IS_OVER_BECAUSE_LAST_UPPER_PIVOT_POINT_IS_BROKEN_WEAK) {
            cell.setCellStyle(cellStyleBackgroundColor(workbook, LIGHT_YELLOW.index));
        }
    }

}
