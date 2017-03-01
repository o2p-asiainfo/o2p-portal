/** 
 * Project Name:o2p-sso2.7 
 * File Name:SHA256.java 
 * Package Name:com.ailk.eaap.op2.sso.framework.util 
 * Date:2016年6月15日下午6:11:06 
 * Copyright (c) 2016, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.utils;  

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

/** 
 * ClassName:SHA256 <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016年6月15日 下午6:11:06 <br/> 
 * @author   wushuzhen 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
public class SHA256 {
	   private static Logger log = Logger.getLog(SHA256.class);
	   public static String encode(String source,String encoding) {
		   String sha256Password=null;
		    try {
		    	MessageDigest  digest = MessageDigest.getInstance("SHA-256");
		        byte[] hash = digest.digest(source.getBytes(encoding));
		         sha256Password = Hex.encodeHexString(hash);
		    } catch (NoSuchAlgorithmException e) {
		        // TODO Auto-generated catch block
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),e));
		    }catch (UnsupportedEncodingException e) {
		        // TODO Auto-generated catch block
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),e));
		    }
			return sha256Password;
	   }
	   
	    public static String encode(String source) {
	        return encode(source,"UTF8");
	    }
}
