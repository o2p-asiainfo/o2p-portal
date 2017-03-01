package com.ailk.o2p.portal.utils;

 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

public class OWASPUtil {
	private static final Logger log = Logger.getLog(OWASPUtil.class);
	
	/**
	 * 安全关闭FileChannel的流
	 * @param objs
	 */
	public static void safeClose(FileChannel... objs) {
		try {
			for(FileChannel obj:objs){
				if (null!=obj) {
					obj.close();
				}
			}
		} catch (IOException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
	}
	
	/**
	 * 安全关闭BufferedReader的流
	 * @param objs
	 */
	public static void safeClose(BufferedReader... objs) {
		try {
			for(BufferedReader obj:objs){
				if (null!=obj) {
					obj.close();
				}
			}
		} catch (IOException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
	}
	
	/**
	 * 安全关闭InputStream的流
	 * @param objs
	 */
	public static void safeClose(InputStream... objs) {
		try {
			for(InputStream obj:objs){
				if (null!=obj) {
					obj.close();
				}
			}
		} catch (IOException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
	}

	/**
	 * 产生一个指定算法生成伪随机数（Long）
	 * @return返回 Long
	 */
	public static Long randomLong(){
		Long randomLong = null;
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			randomLong = sr.nextLong();
		} catch (NoSuchAlgorithmException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		} 
		return randomLong;
	}
	
	/**
	 * 产生一个指定算法生成 base-max 的伪随机数（Integer）
	 * @param max Integer范围的生成的最大值
	 * @param base Integer范围的基础值
	 * @return Integer 
	 */
	public static Integer randomInt(Integer base,Integer max){
		Integer num = null;
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			num = sr.nextInt(max)+base;
		} catch (NoSuchAlgorithmException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		} 
		return num;
	}
	
//	public static void main(String[] args) {
//		for(int i=0;i<10000;i++){
//		}
//	}
}
