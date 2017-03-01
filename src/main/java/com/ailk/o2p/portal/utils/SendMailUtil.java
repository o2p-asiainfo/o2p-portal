package com.ailk.o2p.portal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.asiainfo.foundation.log.Logger;


public class SendMailUtil { 
	
	protected javax.mail.Session session = null;
	private static final Logger log = Logger.getLog(SendMailUtil.class);
	
	//发送邮件的邮箱
	private String sendMailUser = getValueByProCode("sendMail.sendMailUser");	
	//发送邮件的邮箱密码
	private String sendMailPwd = getValueByProCode("sendMail.sendMailPwd");	
	//发送邮件主机
	private String sendSmtpHost = getValueByProCode("sendMail.sendSmtpHost");
	//发送邮件主机的端口
	private String sendSmtpPort = getValueByProCode("sendMail.sendSmtpPort");		
 	
	public SendMailUtil() {
         Properties props = new Properties();          		//先声明一个配置文件以便存储信息
         props.put("mail.transport.protocol", "SMTP");	//首先说明邮件的传输协议
         props.put("mail.smtp.host", sendSmtpHost);  //说明发送邮件的主机地址
         props.put("mail.smtp.auth", "false");         		//说明发送邮件是否需要验证，表示自己的主机发送是需要验证的
         props.put("mail.smpt.port", sendSmtpPort);	//说明邮件发送的端口号
   
         //session认证 getInstance()
         session = javax.mail.Session.getInstance(props,new Authenticator(){
		       public PasswordAuthentication getPasswordAuthentication() {
		           return new PasswordAuthentication(sendMailUser,sendMailPwd);
		       }
         });
         //这个是跟踪后台消息。打印在控制台
         session.setDebug(true);
	}
     
	public void send(String getterMail,String mailContent,String mailTitle) {
         try {
        	 Message msg = new MimeMessage(session);
          	 //设置发送者
             msg.setFrom(new InternetAddress(sendMailUser)); 
             //设置接受者
             msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(getterMail));		//收件邮箱地址
             //设置发送时间
             msg.setSentDate(new Date()); 
             //设置内容的基本机制，字体等
             msg.setContent(mailContent, "text/html;charset=UTF-8"); 
             //设置发送主题
             msg.setSubject(mailTitle);
             //设置邮件内容
             // msg.setText(htmltext); //如果以html的格式发送邮件那么邮件的内容需要通过setContent来设置并且不能用setText
             Transport transport = session.getTransport("smtp"); //得到发送协议
             transport.connect(sendSmtpHost, sendMailUser, sendMailPwd);							//与发送者的邮箱相连
             //发送消息
             transport.sendMessage(msg,msg.getRecipients(Message.RecipientType.TO));   
         } catch (Exception e) {;
        	 LogUtil.log(log, e, "SendMailUtil send Exception:");
         }
     }
     

  	public static String getValueByProCode(String proCode) {
  		Properties p = new Properties();
  		InputStream in = null;
  		try {
  			in = SendMailUtil.class.getResourceAsStream("/mail-send.properties");
  			p.load(in);
  			if(null != in){
  				in.close();
  			}
  			return (String) p.get(proCode);
  		} catch (IOException e) {
  			LogUtil.log(log, e, "SendMailUtil getValueByProCode Exception:");
  			return null;
  		}finally{
  			if(null != in){
  				try {
 					in.close();
 				} catch (IOException e) {
 					LogUtil.log(log, e, "SendMailUtil getValueByProCode Exception:");
 				}
  			}
  		}
  	}

}