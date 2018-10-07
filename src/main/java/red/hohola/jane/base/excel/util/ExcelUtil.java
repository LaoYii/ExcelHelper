package red.hohola.jane.base.excel.util;

import com.google.common.base.Strings;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import red.hohola.jane.base.excel.exception.ExcelFileNotFound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public final class ExcelUtil {

    public static Workbook getWorkbook() throws IOException {
        return getWorkbook(null);
    }

    public static Workbook getWorkbook(File file) throws IOException {
        if (file == null) return new XSSFWorkbook();
        String fileSuffix = FileUtil.getFileSuffix(file.getPath());
        if (file.length() == 0) {
            if (".xls".equals(fileSuffix)) return new HSSFWorkbook();
            else if (".xlsx".equals(fileSuffix)) return new XSSFWorkbook();
            else throw new ExcelFileNotFound(ExcelFileNotFound.FILE_NAME_ERROR);
        } else {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                return getWorkbook(file.getName(), inputStream);
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static Workbook getWorkbook(String fileName, InputStream inputStream) throws IOException {
        String fileSuffix = FileUtil.getFileSuffix(fileName);
        if (fileSuffix != null) {
            if (".xls".equals(fileSuffix)) return new HSSFWorkbook(inputStream);
            else if (".xlsx".equals(fileSuffix)) return new XSSFWorkbook(inputStream);
            else throw new ExcelFileNotFound(ExcelFileNotFound.FILE_NAME_ERROR);
        } else {
            throw new IllegalArgumentException("fileName not null");
        }

    }

    /**
     * 获取工作簿，如果if(newSheet)工作簿存在则新建一个工作簿，反之返回当前工作簿
     *
     * @param wb
     * @param sheetName
     * @param newSheet
     * @return
     */
    public static Sheet getSheet(Workbook wb, String sheetName, boolean newSheet) {
        if (newSheet) {
            int loop = 0;
            while (wb.getSheet(sheetName) != null) {
                if (loop == 0) {
                    sheetName = sheetName + (++loop);
                } else {
                    sheetName = StringUtil.replaceLast(sheetName, String.valueOf(loop), String.valueOf(++loop));
                }
            }
            return wb.createSheet(sheetName);
        } else {
            return getSheet(wb, sheetName);
        }
    }

    public static Sheet getSheet(Workbook wb, String sheetName) {
        Sheet sheet;
        if (!Strings.isNullOrEmpty(sheetName)) {
            sheet = Optional.ofNullable(wb.getSheet(sheetName)).orElseGet(() -> wb.createSheet(sheetName));
        } else {
            sheet = wb.createSheet();
        }
        return sheet;
    }

    public static Sheet getSheetIfNullRtnFirstSheet(Workbook wb, String sheetName) {
        Sheet sheet = null;
        if (!Strings.isNullOrEmpty(sheetName)) {
            sheet = wb.getSheet(sheetName);
        }
        if (sheet == null) {
            return wb.getSheetAt(0);
        } else {
            return sheet;
        }
    }


}