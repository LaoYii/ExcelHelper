import red.hohola.jane.base.excel.annotation.ExcelColumn;
import red.hohola.jane.base.excel.annotation.ExcelSheet;
import red.hohola.jane.base.excel.annotation.ExcelTitle;

import java.util.Date;

@ExcelSheet(sheetName = "示例")
@ExcelTitle("工作簿标题")
public class ExampleBean {

    @ExcelColumn(name = "姓名",sort = 1)
    private String name;

    @ExcelColumn(name = "年龄",sort = 2)
    private int age;

    @ExcelColumn(name = "生日",sort = 3)
    private Date birthday;

    @ExcelColumn(name = "语文",sort = 4)
    private double chinese;

    @ExcelColumn(name = "数学",sort = 4)
    private double math;

    @ExcelColumn(name = "英语",sort = 4)
    private double english;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public double getChinese() {
        return chinese;
    }

    public void setChinese(double chinese) {
        this.chinese = chinese;
    }

    public double getMath() {
        return math;
    }

    public void setMath(double math) {
        this.math = math;
    }

    public double getEnglish() {
        return english;
    }

    public void setEnglish(double english) {
        this.english = english;
    }
}
