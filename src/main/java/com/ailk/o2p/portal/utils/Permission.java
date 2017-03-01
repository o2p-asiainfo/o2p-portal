package com.ailk.o2p.portal.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 方法权限自定义注解.
 * @author NIEZH
 *
 */
@Retention(RetentionPolicy.RUNTIME)//指定该注解是在运行期进行
@Target({ElementType.METHOD})//指定该注解要在方法上使用
public @interface Permission {
	/** 中心名称 */
	String center();
	
	/** 模块名称 */
	String module();

	/** 权限名称 */
	String privilege();
}
