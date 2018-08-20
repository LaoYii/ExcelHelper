package util;

import exception.ExcelFileError;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.Optional;

public final class ExcelUtil {
    
    public static Workbook getWorkbook() throws ExcelFileError{
        return getWorkbook(null);
    }
    
    public static Workbook getWorkbook(File file) throws ExcelFileError{
        String fileSuffix = FileUtil.getFileSuffix(file.getPath());
        if(".xls".equals(fileSuffix)) return new HSSFWorkbook();
        if(file == null || ".xlsx".equals(fileSuffix)) return new XSSFWorkbook();
        else throw new ExcelFileError(ExcelFileError.FILE_NAME_ERROR);
    }

    public static Sheet getSheet(Workbook wb,String sheetName){
        Sheet sheet = null;
        if (StringUtil.isNotNull(sheetName)) {
            int nameloop = 0;
            do{
                if (wb.getSheet(sheetName) != null) {
                    if (nameloop == 0) {
                        sheetName = sheetName+nameloop;
                    }else{
                        sheetName = sheetName.replace(String.valueOf(nameloop), String.valueOf(++nameloop));
                    }
                }else {
                    sheet = wb.createSheet(sheetName);
                    break;
                }
            }while (true);
        }else{
            sheet = wb.createSheet();
        }
        return sheet;
    }
    
    
}