package red.hohola.jane.base.excel;

import com.google.common.base.Strings;
import org.apache.poi.ss.usermodel.*;
import red.hohola.jane.base.excel.enums.DateType;
import red.hohola.jane.base.excel.util.DateUtil;
import red.hohola.jane.base.excel.util.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

final class ExcelReader {
    private static ExcelReader excelReader;

    protected static ExcelReader getExcelWriter() {
        if (excelReader == null) {
            synchronized (ExcelReader.class) {
                if (excelReader == null) {
                    excelReader = new ExcelReader();
                }
            }
        }
        return excelReader;
    }

    protected <T> List<T> read(File file, Class clazz) throws IOException, InstantiationException, IllegalAccessException, ParseException {
        ExcelBean excelBean = new ExcelBean(clazz);
        int dataStartRowNum = excelBean.isHasColumuTitle() ? 2 : 1;
        if (!Strings.isNullOrEmpty(excelBean.getSheetTitle())) {
            dataStartRowNum++;
        }
        Workbook wb = ExcelUtil.getWorkbook(file);
        Sheet sheet = ExcelUtil.getSheetIfNullRtnFirstSheet(wb, excelBean.getSheetName());
        int lastRowNum = sheet.getLastRowNum();
        ArrayList<T> ts = new ArrayList<>();
        Map<Integer, ExcelBean.FieldInfo> fieldMap = collectFileFiled(sheet, excelBean);
        for (int i = dataStartRowNum; i < lastRowNum; i++) {
            ts.add(putData(sheet.getRow(dataStartRowNum),clazz,fieldMap));
        }
        return ts;
    }

    private Map<Integer, ExcelBean.FieldInfo> collectFileFiled(Sheet sheet, ExcelBean excelBean) {
        Map<Integer, ExcelBean.FieldInfo> map = new HashMap<>();
        //检测列名行
        int titleRowNum = Strings.isNullOrEmpty(excelBean.getSheetTitle()) ? 2 : 1;
        Row oneTitle = sheet.getRow(titleRowNum);
        LinkedList<String> oneTitleList = new LinkedList<>();
        for (int i = 0; i < oneTitle.getLastCellNum(); i++) {
            oneTitleList.add(oneTitle.getCell(i).getStringCellValue().trim());
        }
        LinkedList<String> twoTitleList = new LinkedList<>();
        if (excelBean.isHasColumuTitle()) {
            Row twoTitle = sheet.getRow(titleRowNum + 1);
            for (int i = 0; i < twoTitle.getLastCellNum(); i++) {
                twoTitleList.add(twoTitle.getCell(i).getStringCellValue().trim());
            }
        }
        for (ExcelBean.ColumnInfo columnInfo : excelBean.getColumnInfos()) {
            LinkedList<String> findList = Strings.isNullOrEmpty(columnInfo.getTitleName()) ? oneTitleList : twoTitleList;
            for (ExcelBean.FieldInfo fieldInfo : columnInfo.getFieldInfoList()) {
                int titleIndex = findList.indexOf(fieldInfo.getColumnName().trim());
                if (titleIndex != -1) {
                    map.put(titleIndex, fieldInfo);
                }
            }
        }
        return map;
    }

    private <T> T putData(Row row, Class clazz, Map<Integer, ExcelBean.FieldInfo> fieldMap) throws IllegalAccessException, InstantiationException, ParseException {
        Object o = clazz.newInstance();
        for (Map.Entry<Integer, ExcelBean.FieldInfo> entry : fieldMap.entrySet()) {
            Cell cell = row.getCell(entry.getKey());
            setValue(cell,entry.getValue(),o);
        }
        return (T)o;
    }

    public static <T> void setValue(Cell cell, ExcelBean.FieldInfo fieldInfo, T o) throws IllegalAccessException, ParseException {
        Field field = fieldInfo.getField();
        field.setAccessible(true);
        if (field.getType().equals(Date.class)) {
            DateType dateType = fieldInfo.getDateType();
            field.set(o,DateUtil.parse(dateType,cell.getStringCellValue()));
        } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
            field.set(o,cell.getNumericCellValue());
        } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            field.set(o,cell.getBooleanCellValue());
        } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            field.set(o,Integer.valueOf(cell.getStringCellValue()));
        } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
            field.set(o,Long.valueOf(cell.getStringCellValue()));
        } else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
            field.set(o,Float.valueOf(String.valueOf(cell.getNumericCellValue())));
        }else if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
            field.set(o,Short.valueOf(cell.getStringCellValue()));
        }else if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
            field.set(o,Byte.valueOf(cell.getStringCellValue()));
        }else {
            field.set(o,cell.getStringCellValue());
        }
    }


}
