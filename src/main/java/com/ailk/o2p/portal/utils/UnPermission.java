package com.ailk.o2p.portal.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: UnPermission
 * @Description:
 * @author zhengpeng
 * @date 2015-8-3 下午2:34:43
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface UnPermission {

}
