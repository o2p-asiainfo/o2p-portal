package com.ailk.o2p.portal.freeMarker;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import freemarker.cache.URLTemplateLoader;

/**
 * @ClassName: RemoteTemplateLoader
 * @Description:
 * @author zhengpeng
 * @date 2016-7-28 下午2:53:46
 * 
 */
public class RemoteTemplateLoader extends URLTemplateLoader {

	//远程模板文件的存储路径
	private String remotePath;
	private String staticPath;
	private String local;

	public RemoteTemplateLoader(String remotePath,String staticPath) {
		if (remotePath == null) {
			throw new IllegalArgumentException("remotePath is null");
		}
		this.remotePath = canonicalizePrefix(remotePath);
		this.staticPath = canonicalizePrefix(staticPath);
	}

	@Override
	protected URL getURL(String name) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@name:"+name);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Locale locale = RequestContextUtils.getLocale(request);
		local = "_" + locale.toString(); 
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@local:"+local);
		name = name.replace(local, "");
		//name = name.replace("_zh_CN_#Hans", "");
		//name = name.replace("_zh_CN", "");
		//name = name.replace("_zh_HANS_CN", "");
		URL url = null;
		if(name.indexOf("o2pStatic_") == -1){
			String fullPath = this.remotePath + name;
			try {
				url = new URL(fullPath);
				url.openConnection().connect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			String fullPath = this.staticPath + name;
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@fullPath"+fullPath);
			try {
				url = new URL(fullPath);
				url.openConnection().connect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@url"+url);
		return url;
	}
	
	public Reader getReader(Object templateSource, String encoding)
			throws IOException {
		return super.getReader(templateSource, encoding); 
	}

}
