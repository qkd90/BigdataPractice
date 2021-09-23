package com.framework.hibernate.annotation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by dy on 2017/2/22.
 */

@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RegFilter {
        String message() default "{字段值内容不符合要求}"; //提示信息,可以写死,可以填写国际化的key
        String regexp() default "^(\\d{16}|\\d{19})$";
}
