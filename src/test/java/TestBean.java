import annotation.ExcelColumn;
import annotation.ExcelSheet;

import java.util.Date;

@ExcelSheet(sheetName = "TestBean")
public class TestBean extends ExcelBean {

    @ExcelColumn(columnName = "名称",sort = 2)
    private String name;

    @ExcelColumn(columnName = "年龄",sort = 2)
    private int age;

    @ExcelColumn(columnName = "生日",sort = 1)
    private Date birthday;

}
