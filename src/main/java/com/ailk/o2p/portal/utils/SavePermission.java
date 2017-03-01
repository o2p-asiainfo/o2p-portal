package com.ailk.o2p.portal.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 安全权限自定义注解
 * 判断当前登录用户是否有权限执行当前操作
 *  EG   更新产品信息：判断登录用户是否有权限执行(产品提供者、网站管理者有权限)
 * @author zhuangjl
 *
 */
@Retention(RetentionPolicy.RUNTIME)//指定该注解是在运行期进行
@Target({ElementType.METHOD})//指定该注解要在方法上使用
public @interface SavePermission {
	/** 中心名称 */
	String center();
	
	/** 模块名称 */
	String module();
	
	/**对象Id**/
	String parameterKey();

	/** 权限名称 */
	String privilege();
}
