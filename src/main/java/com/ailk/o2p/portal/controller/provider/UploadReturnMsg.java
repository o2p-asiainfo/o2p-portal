package com.ailk.o2p.portal.controller.provider;

/**
 * 图片上传成功或失败返回参数
 * @author 陈伟
 * 2013-09-06
 */
public class UploadReturnMsg implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/** 返回结果  0 失败 1 成功**/
	private String result;
	/** 错误详细消息  **/
	private String errorinfo;
	/** 上传内容md5值 返回**/
	private String c_md5;
	/** 上传内容大小**/
	private long c_size;
	/** 存储url**/
	private String c_url;
	
	/**
     * 封装错误返回消息
     * @return
     */
	public String wrappedErrorMsg(){
    	StringBuilder ret = new StringBuilder();
    	ret.append("{\"result\":\"0\", \"errorinfo\":\"").append(getErrorinfo()).append("\"}");
    	return ret.toString();
    }
    /**
     * 成功消息的封装
     * @return
     */
	public String wrappedSuccessMsg(){
    	StringBuilder ret = new StringBuilder();
    	ret.append("{\"result\":\"1\", \"c_md5\":\"").append(getC_md5()).append("\",\"c_size\":\"")
         .append(getC_size()).append("\",\"c_url\":\"").append(getC_url()).append( "\"}");
    	return ret.toString();
    }
	/**
     * 成功重复消息的封装
     * @return
     */
	public String wrappedRepeatMsg(){
    	StringBuilder ret = new StringBuilder();
    	ret.append("{\"result\":\"2\", \"c_md5\":\"").append(getC_md5()).append("\",\"c_size\":\"")
         .append(getC_size()).append("\",\"c_url\":\"").append(getC_url()).append( "\"}");
    	return ret.toString();
    }
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorinfo() {
		return errorinfo;
	}
	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}
	public String getC_md5() {
		return c_md5;
	}
	public void setC_md5(String c_md5) {
		this.c_md5 = c_md5;
	}
	public long getC_size() {
		return c_size;
	}
	public void setC_size(long c_size) {
		this.c_size = c_size;
	}
	public String getC_url() {
		return c_url;
	}
	public void setC_url(String c_url) {
		this.c_url = c_url;
	}
}