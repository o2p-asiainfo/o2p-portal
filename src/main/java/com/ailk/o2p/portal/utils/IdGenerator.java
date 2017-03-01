package com.ailk.o2p.portal.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGenerator {
private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return random.nextLong();
//		return Math.abs(random.nextLong());
	}

}
