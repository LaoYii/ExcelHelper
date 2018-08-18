package annotation;

/**
 * 自动编码注解
 */
public @interface AutoGeneateCoding {
    String codingColumnName() default "编码";
    int codingStart() default 1;
}
