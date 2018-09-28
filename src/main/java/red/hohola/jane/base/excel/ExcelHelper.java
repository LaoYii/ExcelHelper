package red.hohola.jane.base.excel;

import org.apache.poi.ss.usermodel.Workbook;
import red.hohola.jane.base.excel.util.ExcelUtil;
import red.hohola.jane.base.excel.util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * yes excelHelper
 *
 */
public final class ExcelHelper {

    /**
     * 将excel文件转换为目标bean集合
     * @param filePath
     * @param clazz
     * @param <T extends ExeclBean>
     * @return
     */
    public static <T> List<T> parse(String filePath, Class clazz) {
        
        return parse(new File(filePath),clazz);
    }
    
    public static <T> List<T> parse(File file,Class clazz){
        List<T> read = null;
        try{
            ExcelReader reader = ExcelReader.getExcelWriter();
            read = reader.read(file, clazz);
        } catch (Exception e){
            e.printStackTrace();
        }
        return read;
    }

    /**
     * 将目标bean集合转换为excel文件
     * @param file
     * @param beans
     * @param <T extends ExeclBean>
     * @return
     */
    public static <T> void export(File file, List<T> beans) throws IOException, IllegalAccessException {
        File outFile = FileUtil.checkFileIfExistReturnTimestamp(file);
        Workbook wb = ExcelWriter.getExcelWriter()
                .writer(ExcelUtil.getWorkbook(), beans);
        wb.write(new FileOutputStream(outFile));
    }

    /**
     * 将目标map集合转换为对应的excel文件
     * @param file
     * @param beans
     * @param dataMap
     * @param <T extends ExeclBean>
     * @return
     */
    private static <T> void export(File file, T beans, List<Map<String,Object>> dataMap){
        
    }

    /**
     * 批量导出
     * @param file
     * @param beansList 会根据beas排序排列sheet的顺序
     */
    public static void batchExport(File file,LinkedList<List<Object>> beansList) throws IllegalAccessException, IOException {
        Workbook wb = null;
        for (List<Object> ts : beansList) {
            wb = pvtExport(ts, wb);
        }
        File outFile = FileUtil.checkFileIfExistReturnTimestamp(file);
        wb.write(new FileOutputStream(outFile));
    }

    /**
     * 将excle转换为字节流
     * @param beans
     * @param <T>
     * @return
     */
    public static <T> ByteArrayOutputStream exportToByte(List<T> beans) throws IOException, IllegalAccessException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Workbook wb = pvtExport(beans, ExcelUtil.getWorkbook());
        wb.write(outputStream);
        return outputStream;
    }

    /**
     * 将excle转换为字节流
     * @param beansList
     * @return
     */
    public static ByteArrayOutputStream batchExportToByte(LinkedList<List<Object>> beansList) throws IOException, IllegalAccessException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Workbook wb = null;
        for (List<Object> ts : beansList) {
            wb = pvtExport(ts, wb);
        }
        wb.write(outputStream);
        return outputStream;
    }


    private static <T> Workbook pvtExport(List<T> t, Workbook wb) throws IOException, IllegalAccessException {
        if(wb == null) wb = ExcelWriter.getExcelWriter().writer(ExcelUtil.getWorkbook(), t);
        wb = ExcelWriter.getExcelWriter().writer(wb, t);
        return wb;
    }

}
