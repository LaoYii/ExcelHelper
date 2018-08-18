import annotation.ExcelSheet;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * yes excelHelper
 *
 */
public class ExcelHelper {

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
     * @param out
     * @param beans
     * @param <T extends ExeclBean>
     * @return
     */
    public static <T extends ExcelBean> void export(OutputStream out, List<T> beans){

    }

    /**
     * 将目标map集合转换为对应的excel文件
     * @param out
     * @param beans
     * @param dataMap
     * @param <T extends ExeclBean>
     * @return
     */
    public static <T extends ExcelSheet> void export(OutputStream out, T beans, List<Map<String,Object>> dataMap){

    }

    /**
     * 批量导出
     * @param out
     * @param beansList 会根据beas排序排列sheet的顺序
     * @param <T>
     */
    public static <T extends ExcelSheet> void batchExport(OutputStream out,LinkedList<List<T>> beansList){

    }

    /**
     * 将excle转换为字节流
     * @param beans
     * @param <T>
     * @return
     */
    public static <T extends ExcelSheet> ByteOutputStream export(List<T> beans){

        return null;
    }

}
