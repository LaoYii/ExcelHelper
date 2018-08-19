import exception.ExcelFileError;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;

final class  ExcleUtil {
    
    public static Workbook getWorkbook() throws ExcelFileError{
        return getWorkbook(null);
    }
    
    public static Workbook getWorkbook(File file) throws ExcelFileError{
        String fileSuffix = FileUtil.getFileSuffix(file.getPath());
        if(".xls".equals(fileSuffix)) return new HSSFWorkbook();
        if(file == null || ".xlsx".equals(fileSuffix)) return new XSSFWorkbook();
        else throw new ExcelFileError(ExcelFileError.FILE_NAME_ERROR);
    }
    
    
}