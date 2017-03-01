package com.ailk.o2p.portal.controller.doc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.ailk.o2p.portal.controller.productstore.ProductStoreController;
import com.asiainfo.foundation.log.Logger;

public class HtmlGenerator {
	private HttpClient httpClient = null; // HttpClient实例
	private GetMethod getMethod = null; // GetMethod实例
	private BufferedWriter fw = null;
	private String page = null;
	private String webappname = null;
	private BufferedReader br = null;
	private InputStream in = null;
	private StringBuffer sb = null;
	private String line = null;
	private static final Logger log = Logger.getLog(HtmlGenerator.class);

	// 构造方法
	public HtmlGenerator(String webappname) {
		this.webappname = webappname;
	}

	/** 根据模版及参数产生静态页面 */
	public boolean createHtmlPage(String url, String htmlFileName) {
		boolean status = false;
		int statusCode = 0;
		try {
			// 创建一个HttpClient实例充当模拟浏览器
			httpClient = new HttpClient();
			// 设置httpclient读取内容时使用的字符集
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 创建GET方法的实例
			getMethod = new GetMethod(url);
			// 使用系统提供的默认的恢复策略，在发生异常时候将自动重试3次
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			// 设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
			getMethod.addRequestHeader("Content-Type", "text/html;charset=gbk");
			// 执行Get方法并取得返回状态码，200表示正常，其它代码为异常
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != 200) {
				log.error("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName + "时出错!");
				status = false;
			} else {
				// 读取解析结果
				sb = new StringBuffer();
				in = getMethod.getResponseBodyAsStream();
				br = new BufferedReader(new InputStreamReader(in,"utf-8"));
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				if (br != null)
					br.close();
				page = sb.toString();
				// 将页面中的相对路径替换成绝对路径，以确保页面资源正常访问
				page = formatPage(page);
				// 将解析结果写入指定的静态HTML文件中，实现静态HTML生成
				writeHtml(htmlFileName, page);
				status = true;
			}
		} catch (Exception ex) {
			log.error("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName + "时出错:" + ex.getMessage());
			status = false;
		} finally {
			// 释放http连接
			getMethod.releaseConnection();
		}
		return status;
	}

	// 将解析结果写入指定的静态HTML文件中
	private synchronized void writeHtml(String htmlFileName, String content) throws Exception {
/*		fw = new BufferedWriter(new FileWriter(htmlFileName));
		fw.write(page);
		if (fw != null)
			fw.close();*/
		FileOutputStream fos = new FileOutputStream(htmlFileName); 
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");   
		osw.write(page);  
		osw.flush();   
		osw.close();
	}

	// 将页面中的相对路径替换成绝对路径，以确保页面资源正常访问
	private String formatPage(String page) {
		page = page.replaceAll("\\.\\./\\.\\./\\.\\./", webappname + "/");
		page = page.replaceAll("\\.\\./\\.\\./", webappname + "/");
		page = page.replaceAll("\\.\\./", webappname + "/");
		return page;
	}

	public static void main(String[] args) {
		HtmlGenerator h = new HtmlGenerator("");
		h.createHtmlPage("http://127.0.0.1:8080/o2p-portal-pro/productStore/toIndex.shtml", "/usr/local/apache-tomcat-7.0.70/abc.html");
	}

}
