package org.poem.annotation;

import java.lang.annotation.*;

/**
 * 注释
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShiroOauthodIgnore {

}
