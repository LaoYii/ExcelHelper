package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于ExcelBean
 * sheetName 生成的excel中的sheet名称
 * autoFormat 开启自动表格格式化列的格式，默认开启
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelSheet {
    String sheetName();
    boolean autoColumnFormat() default true;
}
