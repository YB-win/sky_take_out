package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author:懒大王Smile
 * @Date: 2024/3/11
 * @Time: 22:46
 * @Description:
 */

@Target(ElementType.METHOD)//限定该自定义注解只能应用于方法
@Retention(RetentionPolicy.RUNTIME)//注解的保留策略，RUNTIME策略，这意味着这个注解不仅被保留在编译后的字节码中，而且还可以在运行时通过反射被访问
public @interface AutoFill {
    OperationType value();
}
