import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import util.ExcelUtil;
import util.StringUtil;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

final class ExcelWriter {

    private static ExcelWriter excelWriter;

    protected static ExcelWriter getExcelWriter() {
        if (excelWriter == null) {
            excelWriter = new ExcelWriter();
        }
        return excelWriter;
    }

    protected <T extends ExcelBean> Workbook writer(Workbook wb, List<T> beans) {
        T bean = beans.get(0);
        bean.initExcelBean();
        Sheet sheet = ExcelUtil.getSheet(wb, bean.getSheetName());
        addSheetTitle(sheet, bean);
        addColumuTitle(sheet, bean);
        putData(sheet, beans, bean);
        return wb;
    }

    private <T extends ExcelBean> void addSheetTitle(Sheet sheet, T bean) {
        String sheetTitle = bean.getSheetTitle();
        if (StringUtil.isNotNull(sheetTitle)) {
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
                if (StringUtil.isNotNull(ci.getTitleName())) {
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

    private <T extends ExcelBean> void putData(Sheet sheet, List<T> data, T bean) {
        int startRow = dataStartRow(sheet);
        List<Field> fields = bean.getFields();
        for (T d : data) {
            Row row = sheet.createRow(startRow);
            for (int i = 0; i < fields.size(); i++) {
                final int j = i;
                Field field = fields.get(i);
                field.setAccessible(true);
                try {
                    Optional.ofNullable(field.get(d)).ifPresent(o -> {
                        setValue(row.createCell(j),o);
                    });
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
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

    private void setValue(Cell cell, Object o) {
        if (o instanceof Date) {
            cell.setCellValue((Date) o);
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
        }else{
            cell.setCellValue(o.toString());
        }
    }

}