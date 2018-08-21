
# Excel-Helper

------

该项目致力于更快速高效excel数据导出导入，**Excel-Helper** 基于apache-poi进行开发，使用继承ExcelBean，对子类添加注解，作为转换基础，同时支持导出导入 

------


## 使用方式
针对需要导入的数据建立一个bean,该bean继承ExcelBean
使用注解对该bean进行excel标识
@ExcelColumn
该注解用于属性上面，提供了以下注解属性
| 注解属性 | 名称 | 是否必填 | 介绍 | 默认值 |
| ---- | ----: | :----:  | :---: | :---: |
| columnName | 列名 | 是  | 设置该列名称 ||
| sort | 排序 |  是 | 列的排序，如果有两个属性排序为同一个值，则认为该组属性有父类标题 ||
| defaultValue | 默认值 | 否 | 如果该列没有数据自动填充该数据  | "" |
| dateType | 日期类型 |  否 | 时间类型用于日期类型 | yyyy-MM-dd |

@ExcelSheet
该注解用于类上面，提供了以下注解属性
| 注解属性 | 名称 | 是否必填 | 介绍 | 默认值 |
| ---- | ----: | :----:  | :---: | :---: |
| sheetName | 工作簿名称 | 是  | 设置该工作簿名称 ||
| autoFormat | 自动格式化 |  否 | 自动美化excel格式 | true |

@ExcelTitle
该注解用于类和属性上面，提供了以下注解属性
| 注解属性 | 名称 | 是否必填 | 介绍 | 默认值 |
| ---- | ----: | :----:  | :---: | :---: |
| title | 标题 | 是  | 工作簿的大标题或属性组的夫标题 ||


示例：
```java
//ExampleBean.java
import annotation.ExcelColumn;
import annotation.ExcelSheet;
import annotation.ExcelTitle;

import java.util.Date;

@ExcelSheet(sheetName = "示例")
@ExcelTitle(title = "工作簿标题")
public class ExampleBean extends ExcelBean {

    @ExcelColumn(columnName = "姓名",sort = 1)
    private String name;

    @ExcelColumn(columnName = "年龄",sort = 2)
    private int age;

    @ExcelColumn(columnName = "生日",sort = 3)
    private Date birthday;

    @ExcelColumn(columnName = "语文",sort = 4)
    @ExcelTitle(title = "成绩")
    private double chinese;

    @ExcelColumn(columnName = "数学",sort = 4)
    private double math;

    @ExcelColumn(columnName = "英语",sort = 4)
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
```

```java
//Test.javaa
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        ArrayList<ExampleBean> list = new ArrayList<>();
        String[] names = {"写","示","例","脑","壳","疼","好","想","睡","觉"};
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i = 0; i < 10; i++) {
            ExampleBean eb = new ExampleBean();
            int nameLenght = (int)(2+Math.random()*(3-2+1));
            StringBuilder name = new StringBuilder();
            while (nameLenght-- > 0){
                name.append(names[(int) (0 + Math.random() * (10))]);
            }
            eb.setName(name.toString());
            eb.setAge((int) (10 + Math.random() * (20-9)));
            eb.setBirthday(new Date());
            eb.setChinese(Double.valueOf(df.format(1 + Math.random() * (100))));
            eb.setEnglish(Double.valueOf(df.format(1 + Math.random() * (100))));
            eb.setMath(Double.valueOf(df.format(1 + Math.random() * (100))));
            list.add(eb);
        }
        try {
            ExcelHelper.export(new File("outFIle"), list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

```

导出文件



------

## 当前版本 1.1-SNAPSHOT
1. 基础导出功能完成
2. 基础结构完成

## 未来版本 
1. 导入功能
2. 增强型导出功能
3. 自动格式化
4. 减少注解和依赖
5. 增加double格式化