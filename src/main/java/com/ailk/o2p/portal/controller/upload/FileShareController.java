package com.ailk.o2p.portal.controller.upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ailk.eaap.op2.bo.FileShare;
import com.ailk.eaap.op2.common.CommonUtil;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.upload.IFileShareService;
import com.ailk.o2p.portal.utils.UnPermission;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.StringUtil;

/**
 * @ClassName: FileShareController
 * @Description: 
 * @author zhengpeng
 * @date 2015-6-5 上午10:06:56
 *
 */
@Controller
public class FileShareController extends BaseController{
	
	private static final Logger log = Logger.getLog(FileShareController.class);
	@Autowired
	private IFileShareService fileShareService;
	
	@RequestMapping(value = "/fileShare/uploadImage", method = RequestMethod.POST)
	@UnPermission
	@ResponseBody
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request) {
		FileShare fileShare = new FileShare();
		String result = null;
		File tempf = null;
		try {
			String pathName = System.getProperty("java.io.tmpdir") + File.separator + file.getOriginalFilename();
			tempf = new File(pathName);
			FileUtils.copyInputStreamToFile(file.getInputStream(), tempf);
			Path path = Paths.get(tempf.getPath());
			String contentType = Files.probeContentType(path);
			if (contentType.contains("image")) {
				fileShare.setSFileName(file.getName());
				fileShare.setSFileContent(tempf);
				Integer docId = fileShareService.saveFileShare(fileShare);
				result = String.valueOf(docId);
			} else {
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"upload exception: The  file contenrType " + contentType + "; doesn't match.");
			}
		} catch (Exception e) {
			result = null;
			String errorMsg = CommonUtil.getErrMsg(e);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode, "upload exception:" + errorMsg, e));
		} finally {
			if (tempf != null) {
				try {
					tempf.delete();
				} catch (Exception e) {
					String errorMsg = CommonUtil.getErrMsg(e);
					log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"delete exception:" + errorMsg, e));
				}

			}
		}
		return result;
	}
	
    
    @RequestMapping(value = "/fileShare/importImageByPath", method = RequestMethod.GET)  
    @UnPermission
	@ResponseBody
	public String upload(final HttpServletRequest request,
			final HttpServletResponse respons) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		String realPath = request.getSession().getServletContext().getRealPath("/");
    	String result = null;
    	File tempfs = null;
    	//File tempf = null;
    	//String filePath = "E:/portal/image";
    	realPath = realPath+"importImage";
        try {  
        	//tempf = new File(System.getProperty("java.io.tmpdir")+File.separator+file.getOriginalFilename());  
        	System.out.println("realPath:"+realPath);
        	tempfs = new File(realPath);
            File[] tempf = tempfs.listFiles();
            for (int i = 0; i < tempf.length; i++) {
            	FileShare fileShare = new FileShare();
                fileShare.setSFileName(tempf[i].getName()); 
            	fileShare.setSFileContent(tempf[i]); 
            	Integer docId =	fileShareService.saveFileShare(fileShare);
            	tempf[i].delete();
			}
        	//FileUtils.copyInputStreamToFile(file.getInputStream(),tempf); 
        	result = "success";
        } catch (Exception e) {  
        	result = null;
        	String errorMsg = CommonUtil.getErrMsg(e);
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"upload exception:" + errorMsg,e));
        }finally{
/*        	if(tempf != null){
        		try{
        			tempf.delete();
        		}catch(Exception e){
        			String errorMsg = CommonUtil.getErrMsg(e);
        			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"delete exception:" + errorMsg,e));
        		}
        	
        	}*/
        } 
		return result;
	}  
    
    
	@RequestMapping(value = "/fileShare/readImg", method = RequestMethod.GET)
	@ResponseBody
	@UnPermission
	public String readImg(final HttpServletRequest request,
			final HttpServletResponse response) {
		String sFileId = getRequestValue(request, "sFileId");
		if(!StringUtil.isEmpty(sFileId)){
			OutputStream ops = null;
			try {
				FileShare fileShare = new FileShare();
				fileShare.setSFileId(Integer.valueOf(sFileId));
				List<Map> fileShareList = (List<Map>) fileShareService.selectFileShare(fileShare);
				java.sql.Blob blob = null;
				byte[] byteArr = null;
				if (fileShareList != null) {
					for (Map item : fileShareList) {
						if (item.get("S_FILE_CONTENT") != null
								&& item.get("S_FILE_CONTENT").getClass().getName().equals("oracle.sql.BLOB")) {
	
							blob = (Blob) item.get("S_FILE_CONTENT");
							int length = (int) blob.length();
							byteArr = blob.getBytes(1, length);
						}
						if (item.get("S_FILE_CONTENT") != null
								&& item.get("S_FILE_CONTENT").getClass().getName()
										.equals("[B")) {
	
							byteArr = (byte[]) item.get("S_FILE_CONTENT");
						}
	
					}
				}
				response.setContentType("image/png");
	
				ops = response.getOutputStream();
				if (null != byteArr) {
					for (int i = 0; i < byteArr.length; i++) {
						ops.write(byteArr[i]);
					}
				}
	
			} catch (Exception e) {
				String errorMsg = CommonUtil.getErrMsg(e);
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
						ExceptionCommon.WebExceptionCode, errorMsg, null));
			} finally {
				try {
					if (null != ops) {
						ops.flush();
						ops.close();
					}
				} catch (IOException e) {
					String errorMsg = CommonUtil.getErrMsg(e);
					log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
							ExceptionCommon.WebExceptionCode, errorMsg, null));
				}
	
			}
		}
		return null;
	}

}
