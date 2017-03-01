package com.ailk.o2p.portal.utils;

import com.ailk.eaap.op2.common.CommonUtil;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

/**
 * @ClassName: LogUtil
 * @Description: 
 * @author zhengpeng
 * @date 2015-8-3 下午3:56:38
 *
 */
public class LogUtil {
	
	public static void log(final Logger log,Exception e,String msg){
		String errorMsg = CommonUtil.getErrMsg(e);
		log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,msg + errorMsg,e));
	}

}
