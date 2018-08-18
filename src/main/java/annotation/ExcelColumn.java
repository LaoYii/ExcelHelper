package annotation;

import java.lang.annotation.*;
import java.util.Comparator;

/**
 * 该注解用于excelBean的属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ExcelColumn {
    String columnName();
    int high() default 1;
    int sort();
}
