package com.ailk.o2p.portal.freeMarker;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: FreemarkerConfig
 * @Description: 
 * @author zhengpeng
 * @date 2016-7-5 上午10:30:09
 *
 */
@Configuration
public class FreemarkerConfig {
	
	@Bean(name="getFreemarkerConfig")
	public FreeMarkerConfigurer getFreemarkerConfig(){
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		String templatePath = this.getTemplatePath();
		String staticPath= this.getStaticHtmlPath();
		RemoteTemplateLoader remoteTemplateLoader = new RemoteTemplateLoader(templatePath,staticPath);
		freeMarkerConfigurer.setPostTemplateLoaders(remoteTemplateLoader);
		
		//freeMarkerConfigurer.setTemplateLoaderPath(templatePath);
		freeMarkerConfigurer.setDefaultEncoding("utf-8");
		Properties properties = new Properties();
		String templateUpdateDelay = this.getTemplateUpdateDelay(); 
		properties.setProperty("template_update_delay", templateUpdateDelay);
		properties.setProperty("number_format", "#.##");
		//properties.setProperty("locale", "en_US");
		freeMarkerConfigurer.setFreemarkerSettings(properties);
		return freeMarkerConfigurer;
	}
	
	private String getTemplatePath(){
		String templatePath = WebPropertyUtil.getPropertyValue("freemarkerPath");
		if(StringUtil.isEmpty(templatePath)){
			templatePath = "/";
		}
		return templatePath;
	}
	
	private String getStaticHtmlPath(){
		String staticPath = WebPropertyUtil.getPropertyValue("cms_static_path");
		if(StringUtil.isEmpty(staticPath)){
			staticPath = "/";
		}
		return staticPath;
	}
	
	private String getTemplateUpdateDelay(){
		String templateUpdateDelay = WebPropertyUtil.getPropertyValue("template_update_delay");
		if(StringUtil.isEmpty(templateUpdateDelay)){
			templateUpdateDelay = "1";
		}
		return templateUpdateDelay;
	}



}
