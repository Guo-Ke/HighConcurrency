package com.example.concurrency.annocations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记现成不安全的方法或写法
 * @author 郑启
 * @date 2020/07/21 14:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface NoThreadSafe {
	String value() default "";
}
