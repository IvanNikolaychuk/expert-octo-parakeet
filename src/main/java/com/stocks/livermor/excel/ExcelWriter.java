package com.stocks.livermor.excel;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.excel.writers.*;
import com.stocks.livermor.utils.RecordsHolder;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.stocks.livermor.Constants.NULL_DATE;
import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK;

public class ExcelWriter {
    private static final String BASE_PATH = "src/main/resources/livermore/";
    private static final String PATH_TO_TEMPLATE = BASE_PATH + "Livermore.xlsx";
    private static final String PATH_TO_NEW_FILE = BASE_PATH + "Livermore " + date() + ".xlsx";

    private static final int DATE_ROW_COLUMN = 1;
    private static final int SECONDARY_RALLY_COLUMN = 2;
    private static final int NATURAL_RALLY_COLUMN = 3;
    private static final int UPPER_TREND_COLUMN = 4;
    private static final int DOWN_TREND_COLUMN = 5;
    private static final int NATURAL_REACTION_COLUMN = 6;
    private static final int SECONDARY_REACTION_COLUMN = 7;

    private int currentRow;
    private final Workbook workbook;
    private int offset;
    private int prevColumnIndex = -1;

    public ExcelWriter() throws Exception {
        currentRow = 3;
        workbook = new XSSFWorkbook(OPCPackage.open(PATH_TO_TEMPLATE));
    }

    public void next() {
        currentRow = 3;
        prevColumnIndex = -1;

        offset = (offset == 0) ? 6 : offset * 2;
    }


    public void createTable(RecordsHolder recordsHolder) throws Exception {

        for (Record record : recordsHolder.getRecords()) {
            if (record.getDate() != NULL_DATE) {
                DateFieldWriter.write(workbook, getCell(DATE_ROW_COLUMN), record);
            }
            writeRecord(record);
            currentRow++;
        }

        try (FileOutputStream fileOut = new FileOutputStream(PATH_TO_NEW_FILE)) {
            workbook.write(fileOut);
        }
    }

    private void writeRecord(Record record) {
        switch (record.getState()) {
            case SECONDARY_RALLY:
                SecondaryRallyReactionWriter.write(workbook, getCell(SECONDARY_RALLY_COLUMN), record);
                break;
            case NATURAL_RALLY:
                NaturalRallyWriter.write(workbook, getCell(NATURAL_RALLY_COLUMN), record);
                break;
            case DOWN_TREND:
                DownTrendWriter.write(workbook, getCell(DOWN_TREND_COLUMN), record);
                break;
            case UPPER_TREND:
                UpperTrendWriter.write(workbook, getCell(UPPER_TREND_COLUMN), record);
                break;
            case NATURAL_REACTION:
                NaturalReactionWriter.write(workbook, getCell(NATURAL_REACTION_COLUMN), record);
                break;
            case SECONDARY_REACTION:
                SecondaryRallyReactionWriter.write(workbook, getCell(SECONDARY_REACTION_COLUMN), record);
                break;
            case NONE:
                NoneFieldWriter.write(workbook, getCell(prevColumnIndex), record);
        }
    }

    private Cell getCell(int columnIndex) {
        if (columnIndex != DATE_ROW_COLUMN) prevColumnIndex = columnIndex;

        int realIndex = (columnIndex == DATE_ROW_COLUMN) ? columnIndex : columnIndex + offset;
        return workbook.getSheetAt(0)
                .getRow(currentRow)
                .getCell(realIndex, CREATE_NULL_AS_BLANK);
    }

    private static String date() {
        return new SimpleDateFormat("dd-MM").format(new Date());
    }
}