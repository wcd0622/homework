package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LagouSecurity {
    String value() default "";
}
