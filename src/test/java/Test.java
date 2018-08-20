import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {

        /*File file = new File("C:\\Users\\tyhdl\\Desktop\\qqq.xlsx");
        List<TestBean> testBeans = Arrays.asList(new TestBean());
        ExcelHelper.export(file,testBeans);*/
        /*TestBean testBean = new TestBean();
        testBean.initExcelBean();
        List<ExcelBean.ColumnInfo> columnInfos = testBean.getColumnInfos();
        System.out.println();*/
        XSSFWorkbook wb = new XSSFWorkbook();
        add(wb.createSheet());

    }

    public static void add(Sheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        sheet.createRow(0).createCell(0).setCellValue("123wss");
        sheet.createRow(1);
        System.out.println(sheet.getLastRowNum());
    }
}
