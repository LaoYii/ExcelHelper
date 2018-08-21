import annotation.ExcelSheet;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import exception.ExcelFileError;
import org.apache.poi.ss.usermodel.Workbook;
import util.ExcelUtil;
import util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
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
     * @param bean
     * @param <T extends ExeclBean>
     * @return
     */
    public static <T extends ExcelBean> List<T> parse(String filePath, T bean){

        return null;
    }

    /**
     * 将目标bean集合转换为excel文件
     * @param file
     * @param beans
     * @param <T extends ExeclBean>
     * @return
     */
    public static <T extends ExcelBean> void export(File file, List<T> beans) throws ExcelFileError, IOException, IllegalAccessException {
        File outFile = FileUtil.checkFileIfExistReturnTimestamp(file);
        Workbook wb = ExcelWriter.getExcelWriter().writer(ExcelUtil.getWorkbook(), beans);
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
    public static <T extends ExcelBean> void export(File file, T beans, List<Map<String,Object>> dataMap){
        
    }

    /**
     * 批量导出
     * @param file
     * @param beansList 会根据beas排序排列sheet的顺序
     * @param <T>
     */
    public static <T extends ExcelBean> void batchExport(File file,LinkedList<List<T>> beansList) throws ExcelFileError, IllegalAccessException, IOException {
        Workbook wb = null;
        for (List<T> ts : beansList) {
            wb = exportPvt(ts, wb);
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
    public static <T extends ExcelBean> ByteOutputStream exportByte(List<T> beans) throws IOException, IllegalAccessException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        Workbook wb = exportPvt(beans, ExcelUtil.getWorkbook());
        wb.write(byteOutputStream);
        return byteOutputStream;
    }

    /**
     * 将excle转换为字节流
     * @param beansList
     * @param <T>
     * @return
     */
    public static <T extends ExcelBean> ByteOutputStream batchExportByte(LinkedList<List<T>> beansList) throws IOException, IllegalAccessException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        Workbook wb = null;
        for (List<T> ts : beansList) {
            wb = exportPvt(ts, wb);
        }
        wb.write(byteOutputStream);
        return byteOutputStream;
    }


    private static <T extends ExcelBean> Workbook exportPvt(List<T> t,Workbook wb) throws ExcelFileError, IllegalAccessException {
        if(wb == null) wb = ExcelWriter.getExcelWriter().writer(ExcelUtil.getWorkbook(), t);
        wb = ExcelWriter.getExcelWriter().writer(wb, t);
        return wb;
    }

}
