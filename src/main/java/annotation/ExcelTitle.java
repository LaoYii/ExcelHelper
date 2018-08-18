package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果配合ExcelSheet作用为表的抬头名称
 * 如果配合ExcelColumn作用为合并格名称
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface ExcelTitle {
    String title();
}
