package dev.ynnk.m295.helper.patch;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DBPrefer {
}
