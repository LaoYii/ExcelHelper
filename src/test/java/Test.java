import java.util.List;

public class Test {

    public static void main(String[] args) {
        TestBean testBean = new TestBean();
        testBean.initExcelBean();
        List<ExcelBean.ColumnInfo> columnInfos = testBean.getColumnInfos();
        System.out.println();
    }
}
