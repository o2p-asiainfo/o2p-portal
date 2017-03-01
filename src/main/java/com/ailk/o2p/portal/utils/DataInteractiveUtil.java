package com.ailk.o2p.portal.utils;

/*
 * @(#)DataInteractiveUtil.java        2013-4-14
 *
 * Copyright (c) 2013 asiainfo-linkage
 * All rights reserved.
 *
 */

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.foundation.common.ExceptionCommon;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;


/**
 * 类名称<br>
 * 这里是类的描述区域，概括出该的主要功能或者类的使用范围、注意事项等
 * <p>
 * @version 1.0
 * @author Administrator 2013-4-14
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class DataInteractiveUtil {
	
//	public static final String systemCoding=org.apache.struts2.config.DefaultSettings.get("struts.i18n.encoding");// 设置字符集
//	private static final String CONTENT_TYPE = "text/html; charset="+systemCoding;
	private static final Logger log = Logger.getLog(DataInteractiveUtil.class);

	
	
	/**
	 * 处理ajax 发送过来的xml文档，并xml信息转化成Document
	 * @param request 请求对象
	 * @return Document
	 */
	public static Document readToSax(HttpServletRequest request){
	    Document doc = null;
	    try {
	    	request.setCharacterEncoding("UTF-8");
			BufferedReader br = request.getReader();
			String sline = "";
			StringBuffer S=new StringBuffer("");
			while(sline!=null){				
				S = S.append(sline);
				sline=br.readLine();
			}    
			String inString = S.toString();
			Reader in = new StringReader(inString);
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(in);
			return doc;
	    }
	    catch (Exception e) {
	    	log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
	      }
	    return null;
	}	
	
	/**
	 * 用于在Action中因页面提交ajax,向页面输出
	 * @param out
	 * 输出流程
	 * @param request
	 * 请求对象
	 * @param response
	 * 输出对象
	 * @param type
	 * 类型 XML,TEXT
	 * @return void
	 */


	
	public static void actionResponsePage(HttpServletResponse response,String str) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();  
		out.print(str);
		out.flush();
	}	
	
	public static void actionResponsePage(HttpServletResponse response,JSONObject jsonObject) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();  
		out.print(jsonObject);
		out.flush();
	}
	
	/**
	 * 对xml中的特殊字符进行替换
	 * @param str 要替换的字符串
	 * @return String 替换后的字符串
	 */
	public static String antiReplaceXmlSpecial(String str) {
		if ((str != null) && (str.length() > 0)) {
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("&gt;", ">");
			str = str.replaceAll("&lt;", "<");
			str = str.replaceAll("&apos;", "'");
			str = str.replaceAll("&quot;", "\"");
		}
		return str; 
	}
	
	/**
	 * 对字符串的"&","<",">","\","'",进行转义处理
	 * @param str
	 * @return 替换后的字符串
	 */
	public static String doXmlKeyChar(String str){
		str =str.replaceAll("&", "&amp;");
		str =str.replaceAll("<", "&lt;");
		str =str.replaceAll(">", "&gt;");
		str =str.replaceAll("\"", "&quot;");
		str =str.replaceAll("'", "&apos;");
	    return str;		
	}	
}
