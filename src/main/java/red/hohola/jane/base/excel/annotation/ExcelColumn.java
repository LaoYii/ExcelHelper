package red.hohola.jane.base.excel.annotation;

import red.hohola.jane.base.excel.enums.DateType;

import java.lang.annotation.*;

/**
 * 该注解用于excelBean的属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ExcelColumn {
    String name();
    int sort();
    String defaultValue() default "";
    DateType dateType() default DateType.YEAR_MONTH_DAY;
}
