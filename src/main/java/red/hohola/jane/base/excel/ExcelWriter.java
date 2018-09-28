package red.hohola.jane.base.excel;

import com.google.common.base.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import red.hohola.jane.base.excel.enums.DateType;
import red.hohola.jane.base.excel.util.DateUtil;
import red.hohola.jane.base.excel.util.ExcelUtil;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

final class ExcelWriter {

    private static ExcelWriter excelWriter;

    protected static ExcelWriter getExcelWriter() {
        if (excelWriter == null) {
            synchronized (ExcelWriter.class){
                if (excelWriter == null){
                    excelWriter = new ExcelWriter();
                }
            }
        }
        return excelWriter;
    }

    protected <T> Workbook writer(Workbook wb, List<T> beans) throws IllegalAccessException {
        if (beans.size() != 0){
            ExcelBean excelBean = new ExcelBean(beans.get(0).getClass());
            Sheet sheet = ExcelUtil.getSheet(wb, excelBean.getSheetName(),true);
            addSheetTitle(sheet, excelBean);
            addColumuTitle(sheet, excelBean);
            putData(sheet, beans, excelBean);
        }
        return wb;
    }

    private <T extends ExcelBean> void addSheetTitle(Sheet sheet, T bean) {
        String sheetTitle = bean.getSheetTitle();
        if (!Strings.isNullOrEmpty(sheetTitle)) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, bean.getColumnSize() - 1));
            sheet.createRow(0).createCell(0).setCellValue(sheetTitle);
        }
    }

    private <T extends ExcelBean> void addColumuTitle(Sheet sheet, T bean) {
        for (ExcelBean.ColumnInfo ci : bean.getColumnInfos()) {
            int startRow = columuStartRow(sheet);
            Row row = sheet.getRow(startRow);
            if (row == null) row = sheet.createRow(startRow);
            short lastCellNum = row.getLastCellNum();
            int startColumu = lastCellNum == -1 ? 0 : lastCellNum;
            int endColumu = startColumu + ci.getColumnNum() - 1;
            if (bean.isHasColumuTitle()) {
                Row nextRow = sheet.getRow(startRow + 1);
                if (nextRow == null) nextRow = sheet.createRow(startRow + 1);
                batchCreateCell(row, startColumu, endColumu);
                batchCreateCell(nextRow, startColumu, endColumu);
                if (!Strings.isNullOrEmpty(ci.getTitleName())) {
                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, startColumu, endColumu));
                    row.getCell(startColumu).setCellValue(ci.getTitleName());
                    for (ExcelBean.FieldInfo fieldInfo : ci.getFieldInfoList()) {
                        nextRow.getCell(startColumu++).setCellValue(fieldInfo.getColumnName());
                    }
                } else {
                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + 1, startColumu, endColumu));
                    for (ExcelBean.FieldInfo fieldInfo : ci.getFieldInfoList()) {
                        row.getCell(startColumu++).setCellValue(fieldInfo.getColumnName());
                    }
                }
            } else {
                batchCreateCell(row, startColumu, endColumu);
                for (ExcelBean.FieldInfo fieldInfo : ci.getFieldInfoList()) {
                    row.getCell(startColumu++).setCellValue(fieldInfo.getColumnName());
                }
            }
        }
    }

    private <T> void putData(Sheet sheet, List<T> data, ExcelBean bean) throws IllegalAccessException {
        int startRow = dataStartRow(sheet);
        List<ExcelBean.FieldInfo> fields = bean.getFieldInfos();
        for (T d : data) {
            Row row = sheet.createRow(startRow++);
            for (int i = 0; i < fields.size(); i++) {
                setValue(row.createCell(i), fields.get(i), d);
            }
        }
    }


    private void batchCreateCell(Row row, int start, int end) {
        for (int i = start; i <= end; i++) {
            row.createCell(i);
        }
    }

    private int columuStartRow(Sheet sheet) {
        return Optional.ofNullable(sheet.getRow(0)).isPresent() ? 1 : 0;
    }

    private int dataStartRow(Sheet sheet) {
        return sheet.getLastRowNum() + 1;
    }
    
    public static <T> void setValue(Cell cell, ExcelBean.FieldInfo fieldInfo, T d) throws IllegalAccessException {
        Field field = fieldInfo.getField();
        field.setAccessible(true);
        Object o = field.get(d);
        if (o == null) {
            o = fieldInfo.getDefaultValue();
        }
        if (o instanceof Date) {
            DateType dateType = fieldInfo.getDateType();
            cell.setCellValue(DateUtil.getFormat(dateType, (Date) o));
        } else if (o instanceof Double) {
            cell.setCellValue((double) o);
        } else if (o instanceof String) {
            cell.setCellValue((String) o);
        } else if (o instanceof Boolean) {
            cell.setCellValue((boolean) o);
        } else if (o instanceof Calendar) {
            cell.setCellValue((Calendar) o);
        } else if (o instanceof RichTextString) {
            cell.setCellValue((RichTextString) o);
        } else {
            cell.setCellValue(o.toString());
        }
    }
    

}