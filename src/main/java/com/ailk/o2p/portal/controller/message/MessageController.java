package com.ailk.o2p.portal.controller.message;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.message.Message;
import com.ailk.eaap.op2.bo.message.MessageRecipent;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.message.IMessageServ;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;
@Controller
@RequestMapping(value = "/messagePortal")
public class MessageController extends BaseController {
	private Logger log = Logger.getLog(this.getClass());
	@Autowired
	private IMessageServ messageServ;
	
	
	//action method
	@RequestMapping(value = "/showMessageSimple", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String showMessageSimple(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject info = new JSONObject();
		try{
			Org orgBean = getOrg(request);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgBean.getOrgId());
			map.put("begin", 0);
			map.put("end", 30);
			
			Integer msgTotal = messageServ.countMessageForLook(map);
			List<Message> msgList = messageServ.showMessageList(map);
			Collections.sort(msgList);
			
			List<Message> tempList = new ArrayList<Message>();
			if(null != msgList && msgList.size() != 0){
				tempList.addAll(groupByType(msgList,EAAPConstants.MESSAGE_MSG_TYPE_STATION));
				tempList.addAll(groupByType(msgList,EAAPConstants.MESSAGE_MSG_TYPE_TODO));
				tempList.addAll(groupByType(msgList,EAAPConstants.MESSAGE_MSG_TYPE_WORKFLOW));
			}
			msgList = null;
			
			info.put("cntMsg", msgTotal);
			info.put("data", tempList);
			
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return info.toString();
	}
	
	private List<Message> groupByType(List<Message> msgList,Integer MsgType){
		if(null==msgList||msgList.size()==0){
			return null;
		}
		List<Message> tempList = new ArrayList<Message>();
		Integer cnt = 0;
		for(Message msg:msgList){
			if((MsgType+"").equals(msg.getMsgType().toString())){
				tempList.add(msg);
				if(++cnt>3){
					break;
				}
			}
		}
		return tempList;
	}
	
	@RequestMapping(value = "/goMessageList")
	public ModelAndView goMessageList(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		//公共
		messages.add("eaap.op2.portal.portalMessage.webMsg");
		messages.add("eaap.op2.portal.portalMessage.flowMsg");
		messages.add("eaap.op2.portal.portalMessage.todoMsg");
		messages.add("eaap.op2.portal.portalMessage.message");
		messages.add("eaap.op2.portal.portalMessage.history");
		messages.add("eaap.op2.portal.portalMessage.btn.search");
		addTranslateMessage(mv, messages);
		
		mv.addObject("msgType",getRequestValue(request, "msgType"));
		mv.addObject("msgQuertType",getRequestValue(request, "msgQuertType"));
		mv.addObject("lookFalg",getRequestValue(request, "lookFalg"));
		mv.addObject("query",getRequestValue(request, "query"));
		
		mv.setViewName("../message/portalMessageList.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/showMessageList", method = RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String showMessageList(final HttpServletRequest request,
			final HttpServletResponse response){
		JSONObject info = new JSONObject();
		try{
			int pageNum = !"".equals(getRequestValue(request, "pageNum"))&&null!=getRequestValue(request, "pageNum")?Integer.parseInt(getRequestValue(request, "pageNum")):1;
			Integer total = 0;
			Org orgBean = getOrg(request);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orgId", orgBean.getOrgId());
			
			String query = getRequestValue(request, "query");
			String msgQuertType = getRequestValue(request, "msgQuertType");
			String msgType = getRequestValue(request, "msgType");
			String lookFalg = getRequestValue(request, "lookFalg");
			List<Message> msgList = null;
			
			if(null!=query&&!"".equals(query)){
				if("OFFERTODO".equals(msgQuertType)){
					paramMap.put("query", EAAPConstants.WORK_FLOW_MESSAGE_QUERY.replace("#id", query));
				}else if("PRODUCTTODO".equals(msgQuertType)){
					paramMap.put("query", EAAPConstants.WORK_FLOW_MESSAGE_QUERY_PRODUCT.replace("#id", query));
				}else if("APPTODO".equals(msgQuertType)){
					paramMap.put("query", EAAPConstants.WORK_FLOW_MESSAGE_QUERY_APP.replace("#id", query));
				}else if("COMTODO".equals(msgQuertType)){
					paramMap.put("query", EAAPConstants.WORK_FLOW_MESSAGE_QUERY_COM.replace("#id", query));
				}else if("ORGTODO".equals(msgQuertType)){
					paramMap.put("query", EAAPConstants.WORK_FLOW_MESSAGE_QUERY_ORG.replace("#id", query));
				}else{
					paramMap.put("query", query);
				}
			}
				
			if(null!=msgType&&!"".equals(msgType)){
				paramMap.put("msgType", msgType);
			}
			paramMap.put("begin", (pageNum-1)*10);
			paramMap.put("end", 10);

			if(null!=lookFalg&&"LOOKED".equals(lookFalg)){
				paramMap.put("status", new String[]{EAAPConstants.MESSAGE_LOOK_MSG,EAAPConstants.MESSAGE_IGNORE_MSG});
				
				total = messageServ.cntMessageListByStatus(paramMap);
				if(total>0){
					msgList = messageServ.showMessageListByStatus(paramMap);
				}
			}else{
				total = messageServ.countMessageForLook(paramMap);
				if(total>0){
					msgList = messageServ.showMessageList(paramMap);
				}
			}
			
			info.put("total", total);
			info.put("page", pageNum);
			info.put("maxVisible", total%10==0?total/10:(total/10)+1);
			info.put("data", msgList);
			
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return info.toString();
	}
	
	@RequestMapping(value = "/lookMsgById", method = RequestMethod.GET)
	public ModelAndView lookMsgById(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			List<String> messages = new ArrayList<String>();
			// 添加页面上需要国际化的消息
			//公共
			messages.add("eaap.op2.portal.portalMessage.webMsg");
			messages.add("eaap.op2.portal.portalMessage.flowMsg");
			messages.add("eaap.op2.portal.portalMessage.todoMsg");
			messages.add("eaap.op2.portal.portalMessage.btn.submit");
			messages.add("eaap.op2.portal.portalMessage.btn.cancle");
			messages.add("eaap.op2.portal.portalMessage.message");
			addTranslateMessage(mv, messages);
			
			String msgId = getRequestValue(request, "msgId");
			if(null==msgId){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"System error!Message error!! msgId is null!", null);
			}

			Org orgBean = getOrg(request);
			
			Map<String,Object> obs = messageServ.lookMsgById(orgBean.getOrgId().toString(),msgId);
			if(null==obs||obs.size()!=2){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"System error!Message error!!", null);
			}
			Message message = (Message) obs.get("message");
			message.setFormatBeginDate("".equals(StringUtil.valueOf(message.getFormatBeginDate()))?null:DateUtil.convDateToString(message.getMsgBeginDate(), "yyyy-MM-dd"));
			message.setFormatEndDate("".equals(StringUtil.valueOf(message.getFormatEndDate()))?null:DateUtil.convDateToString(message.getMsgEndDate(), "yyyy-MM-dd"));
			
			MessageRecipent msgRec = (MessageRecipent) obs.get("msgRec");
			
			mv.addObject("message",message);
			mv.addObject("msgRec",msgRec);
			
			obs = null;
			
			mv.setViewName("../message/portalMessagesContent.jsp");
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
	@RequestMapping(value = "/ignoreMessage", method = RequestMethod.POST)
	public ModelAndView ignoreMessage(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			String msgId = getRequestValue(request, "msgId");
			Org orgBean = getOrg(request);
			
			if(null==msgId){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"System error!Message error!! msgId is null!", null);
			}
			messageServ.ignoreMessage(orgBean.getOrgId().toString(),msgId);
			mv.setViewName("redirect:./goMessageList.shtml");
		}catch(Exception e){
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			return mv;
		}
		return mv;
	}
	
	@RequestMapping(value = "/submitMsgDecision", method = RequestMethod.POST)
	public ModelAndView submitMsgDecision(final HttpServletRequest request,
			final HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		try{
			String msgRecId = getRequestValue(request, "msgRecId");
			String msgDecision = getRequestValue(request, "msgDecision");
			if(null!=msgRecId){
				MessageRecipent msgRec = new MessageRecipent();
				msgRec.setMsgRecId(new BigDecimal(msgRecId));
				msgRec.setMsgDecision(msgDecision);
				messageServ.submitMsgDecision(msgRec );
				
				mv.setViewName("redirect:./goMessageList.shtml");
			}else{
				throw new BusinessException(ExceptionCommon.WebExceptionCode,"System error!Message error!! msgRecId is null!", null);
			}
		}catch(Exception e){
			mv.setViewName("redirect:/exception/toExceptionIndex.shtml?retCode=0001&retMsg="+e.getMessage()+"&callBack="+request.getRequestURL().toString());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"exception:" + e.getMessage(),null));
		}
		return mv;
	}
	
}
