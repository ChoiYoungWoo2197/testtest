package com.uwo.isms.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.SheetUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class ExcelUtil {

    public final static String BASIC_FONT = "굴림";

    public final static short BASIC_FONT_SIZE = 9;

    public final static int COLUMN_WIDTH_UNIT = 256;

    public final static int MAX_COLUMN_WIDTH = 256;

    public final static int MIN_COLUMN_WIDTH = 10;

    public final static int BASIC_COLUMN_AUTO_WIDTH_MARGIN = 5;

    public static void autoSizeColumn(Sheet sheet, int column) {
        autoSizeColumn(sheet, column, true);
    }

    public static void autoSizeColumn(Sheet sheet, int column, boolean useMergedCells) {
        autoSizeColumn(sheet, column, useMergedCells, BASIC_COLUMN_AUTO_WIDTH_MARGIN);
    }

    public static void autoSizeColumn(Sheet sheet, int column, boolean useMergedCells, int margin) {
        double width = SheetUtil.getColumnWidth(sheet, column, useMergedCells);

        if (width != -1) {
            width *= COLUMN_WIDTH_UNIT;
            int maxColumnWidth = 255 * 256;
            if (width > maxColumnWidth) {
                width = maxColumnWidth;
            }
            sheet.setColumnWidth(column, (int) width );
        }
    }

    public static HSSFFont getBasicHeaderFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(BASIC_FONT_SIZE);
        font.setFontName(BASIC_FONT);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return font;
    }

    public static HSSFFont getBasicBodyFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(BASIC_FONT_SIZE);
        font.setFontName(BASIC_FONT);
        return font;
    }

    public static HSSFCellStyle getBasicHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFont(getBasicHeaderFont(workbook));
        setBorder(cellStyle);
        return cellStyle;
    }

    public static HSSFCellStyle getBasicBodyStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(getBasicBodyFont(workbook));
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        setBorder(cellStyle);
        return cellStyle;
    }

    public static HSSFCellStyle getEditableCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFont(getBasicBodyFont(workbook));
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        setBorder(cellStyle);
        return cellStyle;
    }

    public static HSSFCellStyle setBorder(HSSFCellStyle cellStyle) {
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    public static void setHiddenColumn(Sheet sheet, int column) {
        sheet.setColumnWidth(column, 0);
    }

}
