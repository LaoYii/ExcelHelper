import annotation.ExcelColumn;
import annotation.ExcelSheet;
import annotation.ExcelTitle;

import java.util.Date;

@ExcelSheet(sheetName = "TestBean")
@ExcelTitle(title = "qwe")
public class TestBean extends ExcelBean {

    @ExcelColumn(columnName = "名称",sort = 2)
    private String name;

    @ExcelColumn(columnName = "年龄",sort = 2)
    @ExcelTitle(title = "qwerrr")
    private int age;

    @ExcelColumn(columnName = "生日",sort = 2)
    private Date birthday;

}
