package com.ailk.o2p.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
@Controller
@Transactional
@RequestMapping(value = "/exception")
public class ExceptionController extends BaseController {

	private static final Logger log = Logger.getLog(ExceptionController.class);

	@RequestMapping(value = "/toExceptionIndex")
	public ModelAndView toPardSpecIndex(final HttpServletRequest request,
			final HttpServletResponse response) {
		List<String> messages = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		String retCode = getRequestValue(request,"retCode");
		String retMsg = getRequestValue(request,"retMsg");
		
		mv.addObject("retCode",retCode);
		mv.addObject("retMsg",retMsg);
		
		log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"Exception Center=========>>>" + retMsg));
		
		addTranslateMessage(mv, messages);
		mv.setViewName("../exceptionIndex.jsp");
		return mv;
	}
}
