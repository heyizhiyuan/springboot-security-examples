package com.cnj.common.aspect;

import java.lang.annotation.*;

/**
 * Create by cnj on 2019-2-24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface Log {

    String logStr() default "";
}