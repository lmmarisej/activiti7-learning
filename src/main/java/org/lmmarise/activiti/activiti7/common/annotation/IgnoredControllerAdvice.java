package org.lmmarise.activiti.activiti7.common.annotation;

import java.lang.annotation.*;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 16:15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoredControllerAdvice {
}
