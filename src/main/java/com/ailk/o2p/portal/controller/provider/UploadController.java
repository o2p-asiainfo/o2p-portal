package com.ailk.o2p.portal.controller.provider;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ailk.eaap.op2.bo.FileShare;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.provider.IProviderService;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

@Controller
public class UploadController extends BaseController{

	private static Logger log = Logger.getLog(UploadController.class);
	private static final int MAX_LIMIT_MESSAGE_SIZE = 1*1024*1024;
	@Autowired
	private IProviderService providerService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/provider/upload")
	@ResponseBody
	public String upload(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		FileShare fileShare = new FileShare();
       MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
       //  获得第1张图片（根据前台的name名称得到上传的文件）   
       File file = new File("e://");
      MultipartFile imgFile1  =  multipartRequest.getFile("file"); 
      imgFile1.transferTo(file);
      String fileName = imgFile1.getOriginalFilename();
      fileShare.setSFileName(fileName) ;
	  fileShare.setSFileContent(file) ;
	  UploadReturnMsg  returnMsg = new UploadReturnMsg();
		 if(file.length() > MAX_LIMIT_MESSAGE_SIZE){
			 returnMsg.setC_size(file.length());
			 this.writeToResponse(response, returnMsg.wrappedErrorMsg());
			 return null;
		 }
		 Integer imgId = providerService.saveFileShare(fileShare) ;
		 returnMsg.setC_md5("");
		 returnMsg.setC_size(file.length());
		 returnMsg.setC_url(imgId+"");
		 this.writeToResponse(response, returnMsg.wrappedSuccessMsg());
	    return null;
       } 
	
	   private void writeToResponse(HttpServletResponse res, String msg){
	    	try {
				PrintWriter out = res.getWriter();
				out.print(msg);
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
	    }
  }
