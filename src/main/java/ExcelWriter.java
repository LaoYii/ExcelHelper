import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.ExcelUtil;

import java.util.List;

final class ExcelWriter {
    
    protected static <T extends ExcelBean>  Workbook writer(Workbook wb,List<T> beans){
        int nowRow = 0;
        int nowColumu = 0;
        T bean = beans.get(0);
        bean.initExcelBean();
        Sheet sheet = ExcelUtil.getSheet(wb, bean.getSheetName());
        bean.getSheetTitle();

        return wb;
    }

    private static void addSheetTitle(Sheet sheet,String sheetTitle,int columnSize){

    }
}
