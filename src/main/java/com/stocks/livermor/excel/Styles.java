package com.stocks.livermor.excel;

import org.apache.poi.ss.usermodel.*;

public class Styles {
    public static Font defaultFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("Corbel");
        return font;
    }

    public static Font defaultFont(Workbook workbook, short color) {
        Font font = defaultFont(workbook);
        font.setColor(color);
        return font;
    }

    public static CellStyle defaultCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    public static CellStyle cellStyleWithBorder(Workbook workbook, short borderColor) {
        CellStyle cellStyle = defaultCellStyle(workbook);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBottomBorderColor(borderColor);

        return cellStyle;
    }

    public static CellStyle cellStyleBackgroundColor(Workbook workbook, short backgroundColor) {
        CellStyle cellStyle = defaultCellStyle(workbook);
        cellStyle.setFillForegroundColor(backgroundColor);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyle;
    }

}
